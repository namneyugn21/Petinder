package com.petinder.pet_service.invoker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceInvoker {
    private final RestClient restClient;
    @SuppressWarnings("FieldCanBeLocal")
    private final String ENDPOINT = "http://user-service:8080/user/internal";

    public boolean checkUserIds(UUID userId) {
        return checkUserIds(List.of(userId));
    }

    public boolean checkUserIds(List<UUID> userIds) {
        String uri = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/check")
                .queryParam("userIds", userIds)
                .toUriString();
        log.debug("URI: {}", uri);

        return Boolean.TRUE.equals(restClient.get().uri(uri)
                .retrieve()
                .toEntity(Boolean.class)
                .getBody());
    }
}
