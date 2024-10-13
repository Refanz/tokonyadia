package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAccountRepository extends JpaRepository<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {
}
