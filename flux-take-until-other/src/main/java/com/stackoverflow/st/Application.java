package com.stackoverflow.st;


import lombok.SneakyThrows;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Application starter class.
 */
public class Application implements ApplicationRunner {

    /**
     * Application runner method.
     *
     * @param args startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Execute once started.
     *
     */
    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        Flux.range(1, 5) // produces elements from 1 to 5
                .delayElements(Duration.ofSeconds(1)) // delays emission of each element from above for 1 second
                .takeUntilOther(Mono
                        .just(10) // hot publisher. emits one element

                        // delays '10' for 3 seconds. meaning that it will only
                        // appears in the original Flux in 3 seconds
                        .delayElement(Duration.ofSeconds(3))
                )
                .subscribe(System.out::print);

        Thread.sleep(5_000);
    }
}