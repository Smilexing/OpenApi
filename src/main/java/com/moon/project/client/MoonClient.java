package com.moon.project.client;

import com.moon.project.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("OpenAPIservice")
public interface MoonClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}
