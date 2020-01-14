package com.stackoverflow.st.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class Controller {

    private final WebClient webClient = WebClient.create("http://localhost:8080/call");

    private final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping(path = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MyClass> stream() {
        return Flux
                .range(0, 20) // prepare initial 20 requests
                .window(5) // combine elements in batch of 5 (probably buffer will fit better, have a look)

                .delayElements(Duration.ofSeconds(3)) // for testing purpose you can use this function as well

                .flatMap(flow -> flow
                        // perform an external async call for each element in batch of 10
                        .parallel()
                        .runOn(Schedulers.parallel())

                        // single call
                        // .flatMap(element -> webClient.get().exchange())

                        // several calls
                        .flatMap(element -> Flux
                                .concat(IntStream
                                        .range(0, 5)
                                        .parallel()
                                        .mapToObj(i -> webClient.get().exchange())
                                        .collect(Collectors.toList())
                                )
                        )
                        .map(r -> r.bodyToMono(MyClass.class))
                )

                // subscribe to each response and throw received element further to the stream
                .flatMap(response -> Mono.create(s -> response.subscribe(s::success)))
                .doOnNext(r -> log.info("Response from first service {}", r))

                .window(2) // batch of 2 is ready
                .delayElements(Duration.ofSeconds(5)) // for testing purpose you can use this function as well

                .doOnNext(flow -> log.info("Batch of 2 is ready for second call")) // double check tells that batch is ready
                .flatMap(flow -> flow
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .flatMap(element -> webClient.get().exchange())
                        .map(r -> r.bodyToMono(MyClass.class))
                )
                .flatMap(response -> Mono.create(s -> response.subscribe(s::success)));
    }

    @GetMapping(path = "/call", produces = APPLICATION_JSON_VALUE)
    public Mono<MyClass> counter() {
        return Mono
                .just(new MyClass(counter.incrementAndGet()))
                .delayElement(Duration.ofSeconds(1)); // emulate response time
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyClass {
        public Integer i;
    }

}
