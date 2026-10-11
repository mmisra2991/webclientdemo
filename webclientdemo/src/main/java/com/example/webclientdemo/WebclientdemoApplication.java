package com.example.webclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebclientdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebclientdemoApplication.class, args);
	}
}

@RestController
@RequestMapping("/api")
class UserController{

	private final WebClient webClient;

    UserController(WebClient.Builder builder) {
        this.webClient = builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.build();
	}

    @GetMapping("/user/{id}")
	public Mono<User> getOne(@PathVariable String id){
		return webClient
				.get()
				.uri("users/{id}" , id )
				.retrieve()
				.bodyToMono(User.class);
	}
}

record User(Integer id, String email){}
