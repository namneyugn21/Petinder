package com.petinder.auth_service.repository;

import com.petinder.auth_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
