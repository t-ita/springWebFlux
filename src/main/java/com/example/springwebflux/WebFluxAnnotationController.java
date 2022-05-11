package com.example.springwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class WebFluxAnnotationController {

    @GetMapping("mono")
    Mono<String> mono() {
        return Mono.just("Hello, WebFlux!");
    }

    @GetMapping("flux")
    Flux<String> flux() {
        return Flux.interval(Duration.ofSeconds(1)).map(aLong -> LocalDateTime.now().toString());
    }

}
