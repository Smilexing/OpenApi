package com.moon.gateway.config;


import com.example.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    //白名单
    private static final List<String> IP_WHITE_LIST= Arrays.asList("127.0.0.1","127.0.0.2");


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 用户发送请求到 API 网关——已实现

        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识:"+request.getId());
        log.info("请求路径:"+request.getPath().value());
        log.info("请求方法:"+request.getMethod());
        log.info("请求参数:"+request.getQueryParams());
        log.info("请求来源地址:"+request.getRemoteAddress());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址:"+sourceAddress);
        ServerHttpResponse response = exchange.getResponse();
        //3. 黑白名单:如果它这里不符合我们的请求来源地址，我们就拒绝它访问
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);//返回403
            return response.setComplete();
        }
        //4.用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey=headers.getFirst("accessKey");
        String nonce=headers.getFirst("nonce");
        String timestamp=headers.getFirst("timestamp");
        String sign=headers.getFirst("sign");
        String body=headers.getFirst("body");
        //TODO 实际是去数据库查是否已分配给用户
        if (!"moon".equals(accessKey)){
            return handleNoAuth(response);
        }
        if (Long.parseLong(nonce)>10000L) {
            return handleNoAuth(response);
        }
        // 时间戳：时间和当前时间不超过5分钟
        Long currentTime=System.currentTimeMillis()/1000;
        Long FIVE_MINUTES=60 * 5L;
        if ((currentTime-Long.parseLong(timestamp))>=FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        //TODo 实际情况是从数据库中查询出 secretKey
        String serverSign= SignUtil.getSign(body,"abcdefg");
        if (!sign.equals(serverSign)){
            return handleNoAuth(response);
        }
        //5. 请求的模拟接口是否存在？
        //todo 从数据库中查询模拟接口是否存在，以及请求方法是否匹配
        //6. 请求转发，调用模拟接口
        Mono<Void> filter = chain.filter(exchange);
        //7. 响应日志
        return handleResponse(exchange,chain);
        //log.info("custom global filter");
        //return chain.filter(exchange);
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // TODO 7. 调用成功，接口调用次数+1
                                // data从这个content中读取
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);// 释放掉内存
                                // 6.构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);// data
                                sb2.append(data);
                                log.info("响应结果："+data);// log.info("<-- {} {}", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 无权限
     */
    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 接口调用异常
     * @param response
     * @return
     */
    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
