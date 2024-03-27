package com.example.exclude.client;


/**
 * @author chenliang
 */
/*
public class MoonClient {

    private  String acccessKey;
    private  String secretKey;

    public MoonClient(String acccessKey, String secretKey) {
        this.acccessKey = acccessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }
    public String getNameByPost(@RequestParam String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    */
/**
     * 添加请求头
     * @return
     *//*

    private Map<String,String> getHeaders(String body){
        Map<String,String> headers = new HashMap<>();
        headers.put("accessKey",acccessKey);
        */
/*//*
/密码一定不能在服务器间传递
        headers.put("secretKey",secretKey);*//*

        headers.put("nonce", RandomUtil.randomNumbers(4));
        headers.put("body",body);
        headers.put("timestremp",String.valueOf(System.currentTimeMillis()/1000));
        headers.put("sign",getSign(body,secretKey));
        return headers;
    }



    */
/**
     * 服务端校验
     * @param user
     * @return
     *//*

    public String getNameByBody(@RequestBody User user){
        //找到restful请求，这里发送的就是JSON格式
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
*/
