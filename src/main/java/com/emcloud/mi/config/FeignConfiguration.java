package com.emcloud.mi.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.emcloud.mi")
public class FeignConfiguration {

}
