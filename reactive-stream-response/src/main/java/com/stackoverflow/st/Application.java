package com.stackoverflow.st;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application starter class.
 */
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