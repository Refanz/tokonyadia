package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MerchantRepository extends JpaRepository<Merchant, String>, JpaSpecificationExecutor<Merchant> {
}
