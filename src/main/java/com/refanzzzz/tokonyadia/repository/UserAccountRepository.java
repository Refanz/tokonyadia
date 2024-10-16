package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {
    Optional<UserAccount> findUserAccountsByUsername(String username);

    Boolean existsUserAccountByUsername(String username);
}
