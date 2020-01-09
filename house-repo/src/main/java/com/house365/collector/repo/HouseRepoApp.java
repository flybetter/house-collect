package com.house365.collector.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 房源仓库App
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.house365.collector.base.entity"})
@EnableJpaAuditing
public class HouseRepoApp {

    private static final Logger logger = LoggerFactory.getLogger(HouseRepoApp.class);

    public static void main(String[] args) throws Exception {

        SpringApplication.run(HouseRepoApp.class, args);
    }

}
