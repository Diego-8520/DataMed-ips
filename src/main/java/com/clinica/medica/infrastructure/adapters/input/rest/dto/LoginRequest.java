package com.clinica.medica.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
