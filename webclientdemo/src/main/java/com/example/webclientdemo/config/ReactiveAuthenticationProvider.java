package com.example.webclientdemo.config;

import jakarta.annotation.Nonnull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import reactor.core.publisher.Mono;

public class ReactiveAuthenticationProvider implements ReactiveAuthenticationManager {
    @Override
    public @Nullable Mono<Authentication> authenticate(@Nonnull Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();

        if("qwe123".equals(name)){
            return Mono.just(UsernamePasswordAuthenticationToken
                    .authenticated(
                            "qwe123",
                            null,
                            AuthorityUtils.createAuthorityList("ROLE_user")
                    ));
        }
        return Mono.empty();
    }
}
