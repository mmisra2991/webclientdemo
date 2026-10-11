package com.example.webclientdemo.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class RobotFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("x-robot-auth");

        if(authHeader == null){
            return chain.filter(exchange);
        }

        if("beep-boop".equals(authHeader)){
            SecurityContextImpl securityContext = new SecurityContextImpl(new RobotAuthentication());

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder
                            .withSecurityContext(Mono.just(securityContext)));
        }

        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        var bytes = ("You are not Ms robot  🤖🚫 !!! ") .getBytes(StandardCharsets.UTF_8) ;
        var buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse()
                .writeWith(Mono.just(buffer));
    }
}
