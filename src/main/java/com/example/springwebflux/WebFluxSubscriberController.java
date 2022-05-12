package com.example.springwebflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("subscribe")
public class WebFluxSubscriberController {

    private final WebClient client;

    public WebFluxSubscriberController(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("greeting")
    public Mono<String> subscribeGreeting() {
        return client.get().uri("publish/greeting").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> "Subscribe: " + s);
    }

    @GetMapping("fizzbuzz")
    public Flux<String> fizzBuzz() {
        return client.get().uri("publish/numberstream").accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(Long.class)
                .map(aLong -> (aLong%3==0?"Fizz":"")+(aLong%5==0?"Buzz":aLong%3==0?"":aLong));
    }
}
