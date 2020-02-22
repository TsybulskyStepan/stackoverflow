package com.stackoverflow.st.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static reactor.core.scheduler.Schedulers.elastic;

@Slf4j
@RestController
public class Controller {

    private final WebClient webClient = WebClient.create("http://localhost:8080/call");

    @GetMapping(path = "/call", produces = APPLICATION_JSON_VALUE)
    public Mono<Integer> counter() {
        return Mono
                .just(1)
                .delayElement(Duration.ofSeconds(2)); // emulate response time
    }

    @Async
    @Scheduled(cron = "*/10 * * * * *")
    public void job() {
        log.info("Cron job has been triggered");
        Flux<ResponseEntity<Void>> externalCall = Flux
                .range(1, 5)
                .parallel()
                .runOn(elastic())
                .flatMap(i -> {
                    log.info("Call external service {}", i);
                    return webClient.get().retrieve().toBodilessEntity();
                })
                .sequential();

        Flux
                .from(externalCall)
                .thenMany(Flux.defer(
                        () -> {
                            log.info("First execution completed. Call external service one more time");
                            return externalCall;
                        })
                )
                .last()
                .subscribe(i -> log.info("Execution completed"));
    }

}
