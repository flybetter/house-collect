package com.house365.collector.proxy.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProxyProviderBootstrap {

    public static void main(String[] args) {

        SpringApplication.run(ProxyProviderBootstrap.class, args);
    }
}
