package com.codebeforedawn.be;

import org.springframework.boot.SpringApplication;

public class TestBeApplication {

    public static void main(String[] args) {
        SpringApplication.from(BeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
