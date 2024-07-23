package com.petinder.auth_service.invoker;

import com.petinder.auth_service.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceInvoker {
    private final RestClient restClient;
    private final String ENDPOINT = "http://user-service:8080/user";

    @Async
    public void addNewUser(UserInfo userInfo) {
        String uri = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .toUriString();
        log.debug("URI: {}", uri);


        restClient.post().uri(uri)
                .body(userInfo)
                .retrieve()
                .toBodilessEntity();
    }
}
