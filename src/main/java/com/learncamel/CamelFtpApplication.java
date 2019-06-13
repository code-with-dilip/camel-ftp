package com.learncamel;

import com.learncamel.annotation.ExcludeFromTests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ExcludeFromTests
public class CamelFtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelFtpApplication.class, args);
    }

}
