package com.moon.project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeInterfaceCount() {
        boolean b = userInterfaceInfoService.invokeInterfaceCount(1L, 1L);
        Assertions.assertTrue(b);

    }
}