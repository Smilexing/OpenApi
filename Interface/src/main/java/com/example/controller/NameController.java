package com.example.controller;

import com.example.model.User;
import com.example.utils.SignUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class NameController {
    @GetMapping("/name")
    public String getNameByGet(String name,HttpServletRequest request){
        System.out.println(request.getHeader("yupi"));

        return "get你的名字："+name;
    }
    @PostMapping("/")
    public String getNameByPost(@RequestParam String name){
        return "post你的名字："+name;
    }
    @PostMapping("/user")
    public String getNameByBody(@RequestBody User user, HttpServletRequest request){
        String accessKey=request.getHeader("accessKey");
        String nonce=request.getHeader("nonce");
        String timestramp=request.getHeader("timestramp");
        String sign=request.getHeader("sign");
        String body=request.getHeader("body");
        //TODO 实际是去数据库查是否已分配给用户
        if (!accessKey.equals("moon")){
            throw new RuntimeException("无权限");
        }
        //这里不校验随机数了
        if (Long.parseLong(nonce)>10000) {
            throw new RuntimeException("无权限");
        }
        //TODO 时间戳：时间和当前时间不超过5分钟
        //if(timestramp){}
        //TODo 实际情况是从数据库中查询出 secretKey
        String serverSign= SignUtil.getSign(body,"abcdefg");
        if (!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }
        return "post用户的名字："+user.getUsername();
    }
}
