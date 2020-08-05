package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
    @EnableDiscoveryClient 和 @EnableEurekaClient
        共同点：都是能够让注册中心能够发现，扫描到该服务
        不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient可以是其他注册中心（eureka/zk）
 */
@SpringBootApplication
@EnableDiscoveryClient //声明是Eureka客户端
@EnableFeignClients //开启feign支持
public class SystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class, args);
    }

}
