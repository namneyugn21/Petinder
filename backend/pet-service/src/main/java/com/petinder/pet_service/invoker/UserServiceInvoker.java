package com.petinder.pet_service.invoker;

import com.petinder.pet_service.dto.comm.RegisterPetInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceInvoker {
    private final RestClient restClient;
    private final String ENDPOINT = "http://user-service:8080/user";

    public void registerPet(UUID userId, UUID petId) {
        String uri = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .path("/" + userId + "/pet")
                .toUriString();
        log.debug("URI: {}", uri);

        RegisterPetInput input = new RegisterPetInput(petId);
        restClient.post().uri(uri)
                .body(input)
                .retrieve()
                .toBodilessEntity();
    }
}
