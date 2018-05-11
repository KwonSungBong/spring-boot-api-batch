package com.example.demo;

import com.example.demo.component.BatchComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private BatchComponent batchComponent;

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
        log.info("run. args length : {}, args : {}", args.length, String.join(",", args));

//        batchComponent.task();
    }

}
