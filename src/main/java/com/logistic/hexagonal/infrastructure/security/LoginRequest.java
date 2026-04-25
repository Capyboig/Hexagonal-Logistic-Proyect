package com.logistic.hexagonal.infrastructure.security;

public record LoginRequest(String email, String password) {
}
