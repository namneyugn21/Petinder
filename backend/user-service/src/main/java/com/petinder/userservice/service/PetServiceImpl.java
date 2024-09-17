package com.petinder.userservice.service;

import com.petinder.userservice.config.RabbitMqConfig;
import com.petinder.userservice.dto.comm.ReadPetOutput;
import com.petinder.userservice.model.UserPet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final RestClient restClient;
    private final TopicExchange topicExchange;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.application.services.pet-service}")
    private String ENDPOINT;

    public List<ReadPetOutput> getPets(final List<UUID> petIds) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/internal");
        petIds.forEach(id -> builder.queryParam("petIds", id));

        return restClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<ReadPetOutput> getPetsAfter(final UUID petId) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .pathSegment("internal", "from")
                .queryParam("id", petId.toString());

        return restClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public boolean checkPets(final List<UUID> petIds) {
        if (petIds == null || petIds.isEmpty()) {
            return false;
        }

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/internal/check");
        petIds.forEach(id -> builder.queryParam("petIds", id));

        return Boolean.TRUE.equals(
                restClient.get()
                        .uri(builder.toUriString())
                        .retrieve()
                        .body(Boolean.class)
        );
    }

    @Override
    public void likePet(final UserPet userPet) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), RabbitMqConfig.LIKE_PET, userPet);
        log.info("Sent {} to like queue", userPet);
    }

    @Override
    public void dislikePet(final UserPet userPet) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), RabbitMqConfig.DISLIKE_PET, userPet);
        log.info("Sent {} to dislike queue", userPet);
    }
}
