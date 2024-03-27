package com.example;

import com.example.client.MoonClient;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class InterfaceApplicationTests {

    @Resource
    private MoonClient moonClient;
    /**
     * SDK 依赖引入
     */
    @Test
    void SDKTest(){
        String result01 = moonClient.getNameByGet("moon");
        String result02 = moonClient.getNameByPost("moon");
        User user=new User();
        user.setUsername("moonsd");
        String result03 = moonClient.getNameByBody(user);
        System.out.println(result01);
        System.out.println(result02);
        System.out.println(result03);
    }
    /**
     * API 签名认证测试
     */
    /*@Test
    void InterfaceTest() {
        String accessKey="moon";
        String secretKey="abcdefg";
        MoonClient moonClient=new MoonClient(accessKey,secretKey);
        String result01 = moonClient.getNameByGet("moon");
        String result02 = moonClient.getNameByPost("moon");
        User user =new User();
        user.setUsername("moonsd");
        String result03 = moonClient.getNameByBody(user);
        System.out.println(result01);
        System.out.println(result02);
        System.out.println(result03);
    }*/


}
