package com.petinder.auth_service.repository;

public interface RedirectRepository {
    String findByState(String state);

    void removeByState(String state);

    void saveRedirect(String state, String redirect);
}
