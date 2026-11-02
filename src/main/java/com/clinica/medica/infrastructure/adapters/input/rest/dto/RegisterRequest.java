package com.clinica.medica.infrastructure.adapters.input.rest.dto;

import com.clinica.medica.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        Role rol) {}
