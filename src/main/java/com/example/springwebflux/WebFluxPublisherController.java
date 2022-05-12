package com.example.springwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class WebFluxPublisherController {

    @GetMapping("greeting")
    Mono<String> greeting() {
        return Mono.just("Hello, WebFlux!");
    }

    @GetMapping("numberstream")
    Flux<Long> numberStream() {
        return Flux.interval(Duration.ofMillis(500)); // 0.5秒毎に数値を0からカウントアップして返す
    }

}
