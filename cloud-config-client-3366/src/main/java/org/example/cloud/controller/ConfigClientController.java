package org.example.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {
    @Value("${spring.cloud.config.info}")
    private String configInfo;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return "name:" + applicationName + "  info:" + configInfo;
    }
}

