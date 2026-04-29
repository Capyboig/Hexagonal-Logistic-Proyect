package com.logistic.hexagonal.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {
    Optional<UsuarioAuth> findByEmail(String email);
}
