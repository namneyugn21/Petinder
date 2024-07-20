package com.petinder.userservice.invoker;

import com.petinder.userservice.dto.pet.ReadPetOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceInvoker {
    private final WebClient webClient;
    private final String ENDPOINT = "http://pet-service:8080/pet";

    @Async
    public CompletableFuture<Mono<List<ReadPetOutput>>> getPets(
            List<UUID> petIds
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/internal");
        for (UUID id : petIds) {
            builder.queryParam("petIds", id);
        }
        String uri = builder.toUriString();
        log.debug("URI: {}", uri);

        Flux<ReadPetOutput> pets = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(ReadPetOutput.class);

        return CompletableFuture.completedFuture(pets.collectList());
    }
}
