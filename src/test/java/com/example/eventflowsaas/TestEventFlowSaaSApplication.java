package com.example.eventflowsaas;

import org.springframework.boot.SpringApplication;

public class TestEventFlowSaaSApplication {

    public static void main(String[] args) {
        SpringApplication.from(EventFlowSaaSApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
