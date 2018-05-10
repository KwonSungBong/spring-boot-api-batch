package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
@EnableBatchProcessing
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void onInit() {
        log.info("onInit");
    }

    @PreDestroy
    public void onDestory() {
        log.info("onDestory");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("run {}", args);
    }

}
