package com.petinder.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String LIKE_PET = "pet.like";
    public static final String DISLIKE_PET = "pet.dislike";

    public static final String CREATE_USER = "user.create";

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("petinder.backend.topic");
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    private final static class ReceiverConfig {
        @Bean
        public Queue createUser() {
            return new Queue(CREATE_USER);
        }

        @Bean
        public Binding bindingUserCreate(
                TopicExchange topic,
                Queue like
        ) {
            return BindingBuilder.bind(like)
                    .to(topic)
                    .with(CREATE_USER);
        }
    }
}