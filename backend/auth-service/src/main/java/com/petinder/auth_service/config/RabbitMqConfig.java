package com.petinder.auth_service.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String CREATE_USER = "user.create";

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("petinder.backend.topic");
    }
}