package com.petinder.auth_service.repository.redirect;

import jakarta.annotation.Nonnull;

public interface RedirectRepository {
    @Nonnull
    String findAndDeleteByState(String state);

    void saveRedirect(String state, String redirect);
}
