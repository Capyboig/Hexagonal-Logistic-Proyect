package com.logistic.hexagonal.infrastructure.security.rest;

import com.logistic.hexagonal.infrastructure.security.dto.AuthResponse;
import com.logistic.hexagonal.infrastructure.security.dto.LoginRequest;
import com.logistic.hexagonal.infrastructure.security.jwt.JwtService;
import com.logistic.hexagonal.infrastructure.security.persistence.Rol;
import com.logistic.hexagonal.infrastructure.security.persistence.UsuarioAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioAuthRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioAuthRepository repository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(request.email());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@RequestBody LoginRequest request) {
        UsuarioAuth nuevoUsuario = new UsuarioAuth(
                request.email(),
                passwordEncoder.encode(request.password()),
                Rol.REPARTIDOR
        );
        repository.save(nuevoUsuario);

        String token = jwtService.generateToken(nuevoUsuario.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}