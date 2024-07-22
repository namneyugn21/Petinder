package com.petinder.userservice.invoker;

import com.petinder.userservice.dto.comm.ReadPetOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceInvoker {
    private final RestClient restClient;
    private final String ENDPOINT = "http://pet-service:8080/pet";

    @Async
    public CompletableFuture<List<ReadPetOutput>> getPets(
            List<UUID> petIds
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/internal");
        for (UUID id : petIds) {
            builder.queryParam("petIds", id);
        }
        String uri = builder.toUriString();
        log.debug("URI: {}", uri);

        List<ReadPetOutput> response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return CompletableFuture.completedFuture(response);
    }
}
