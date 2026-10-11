package com.example.webclientdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange(exchangeSpec -> {
                    exchangeSpec
                            .pathMatchers("/private", "/user/**").authenticated()
                            .pathMatchers("/","/public").permitAll();
                })
                .addFilterAt(new RobotFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();

    }

    @Bean
    ReactiveUserDetailsService userDetailsService(){
        return new MapReactiveUserDetailsService(
                User.withUsername("qwe")
                        .password("{noop}qwe")
                        .authorities("ROLE_user")
                        .build()
        );
    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}
