package com.clinica.medica.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record EpsRequest(
        @NotBlank(message = "El NIT es obligatorio") String nit,
        @NotBlank(message = "El nombre es obligatorio") String nombre) {}
