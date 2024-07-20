package com.petinder.auth_service.repository;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedirectRepositoryImpl implements RedirectRepository {

    private final Map<String, String> redirectByState;

    public RedirectRepositoryImpl() {
        this.redirectByState = new ConcurrentHashMap<>();
    }

    @Override
    public String findByState(String state) {
        return redirectByState.get(state);
    }

    @Override
    public void saveRedirect(String state, String redirect) {
        redirectByState.put(state, redirect);
    }

    @Override
    public void removeByState(String state) {
        redirectByState.remove(state);
    }
}
