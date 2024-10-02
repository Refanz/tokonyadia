package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entitiy.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}
