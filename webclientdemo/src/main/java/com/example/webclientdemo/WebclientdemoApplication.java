package com.example.webclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class WebclientdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebclientdemoApplication.class, args);
	}
}

@RestController
class UserController{

	private final WebClient webClient;

    UserController(WebClient.Builder builder) {

		this.webClient = builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.build();
	}

	@GetMapping("/public")
	public String hello(){
		return "Hey JPMC devs!";
	}

	@GetMapping("/private")
	public String accessPrivate(){
		return "I am private";
	}

    @GetMapping("/user/{id}")
	public Mono<User> getOne(@PathVariable String id){
//		AtomicReference<String> authUser = new AtomicReference<>("");
//		ReactiveSecurityContextHolder.getContext()
//				.map(securityContext -> {
//					Authentication authentication = securityContext.getAuthentication();
//					UserDetails principal = (UserDetails) authentication.getPrincipal();
//					authUser.set(principal.getUsername());
//					return principal.getUsername();
//				});

//		IO.println("AutheUser is : " + authUser.get());

		return webClient
				.get()
				.uri("users/{id}" , id )
				.retrieve()
				.bodyToMono(User.class);
	}
}

record User(Integer id, String email){}
