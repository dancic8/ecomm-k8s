package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient inventoryWebClient() {
        return WebClient.builder()
                .baseUrl("http://inventory-service:8081/api")
                .build();
    }
}