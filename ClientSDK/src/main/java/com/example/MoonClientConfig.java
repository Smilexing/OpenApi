package com.example;

import com.example.client.MoonClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties("moon.client")
@ComponentScan
public class MoonClientConfig {

    private  String accessKey;
    private  String secretKey;

    @Bean
    public MoonClient moonClient(){
        return new MoonClient(accessKey,secretKey);
    }
}
