package com.petinder.pet_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class RabbitMqConfig {
    public static final String LIKE_PET = "pet.like";
    public static final String DISLIKE_PET = "pet.dislike";

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("petinder.backend.topic");
    }

    @Bean
    public SimpleMessageConverter converter() {
        final SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("java.util.*"));
        return converter;
    }

    private final static class ReceiverConfig {
        @Bean
        public Queue like() {
            return new Queue(LIKE_PET);
        }

        @Bean
        public Binding bindingPetLike(
                TopicExchange topic,
                Queue like
        ) {
            return BindingBuilder.bind(like)
                    .to(topic)
                    .with(LIKE_PET);
        }

        @Bean
        public Queue dislike() {
            return new Queue(DISLIKE_PET);
        }

        @Bean
        public Binding bindingPetDisLike(
                TopicExchange topic,
                Queue like
        ) {
            return BindingBuilder.bind(like)
                    .to(topic)
                    .with(DISLIKE_PET);
        }
    }
}
