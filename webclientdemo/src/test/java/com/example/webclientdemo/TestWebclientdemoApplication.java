package com.example.webclientdemo;

import org.springframework.boot.SpringApplication;

public class TestWebclientdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(WebclientdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
