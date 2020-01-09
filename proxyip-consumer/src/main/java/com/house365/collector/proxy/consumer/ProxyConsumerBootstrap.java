package com.house365.collector.proxy.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProxyConsumerBootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {

        SpringApplication.run(ProxyConsumerBootstrap.class, args);
    }
}
