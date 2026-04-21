package com.logistic.hexagonal.infrastructure.persistence.repository;


import com.logistic.hexagonal.infrastructure.persistence.entity.PaqueteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataPaqueteRepository extends JpaRepository<PaqueteEntity, UUID> {
    Optional<PaqueteEntity> findByTrackingId(String trackingId);
}
