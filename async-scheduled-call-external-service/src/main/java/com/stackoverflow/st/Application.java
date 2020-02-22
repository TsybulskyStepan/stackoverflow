package com.stackoverflow.st;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application starter class.
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Application {

    /**
     * Application runner method.
     *
     * @param args startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}