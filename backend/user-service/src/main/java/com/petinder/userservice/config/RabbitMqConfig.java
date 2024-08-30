package com.petinder.userservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String LIKE_PET = "pet.like";
    public static final String DISLIKE_PET = "pet.dislike";

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("petinder.backend.topic");
    }
}