package com.clinica.medica.infrastructure.adapters.input.rest;

import com.clinica.medica.domain.model.Role;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.AuthResponse;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.LoginRequest;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.RegisterRequest;
import com.clinica.medica.infrastructure.adapters.output.persistence.UsuarioEntity;
import com.clinica.medica.infrastructure.adapters.output.persistence.UsuarioJpaRepository;
import com.clinica.medica.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UsuarioJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            UsuarioJpaRepository repository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Role role = Role.PACIENTE;
        repository.save(new UsuarioEntity(
                null, request.email(), passwordEncoder.encode(request.password()), role, true));
        UserDetails user = userDetailsService.loadUserByUsername(request.email());
        return new AuthResponse(jwtService.generateToken(user));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        return new AuthResponse(jwtService.generateToken(
                userDetailsService.loadUserByUsername(request.email())));
    }
}
