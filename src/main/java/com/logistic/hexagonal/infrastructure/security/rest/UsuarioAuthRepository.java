package com.logistic.hexagonal.infrastructure.security.rest;

import com.logistic.hexagonal.infrastructure.security.persistence.UsuarioAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {
    Optional<UsuarioAuth> findByEmail(String email);
}
