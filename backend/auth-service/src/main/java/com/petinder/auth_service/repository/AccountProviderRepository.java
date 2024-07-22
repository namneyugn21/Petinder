package com.petinder.auth_service.repository;

import com.petinder.auth_service.model.AccountProvider;
import com.petinder.auth_service.model.AccountProviderKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProviderRepository extends JpaRepository<AccountProvider, AccountProviderKey> {
}
