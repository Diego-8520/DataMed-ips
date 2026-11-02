package com.clinica.medica.domain.model;

public record Eps(Long id, String nit, String nombre, Boolean activo) {
    public Eps {
        activo = activo == null || activo;
    }
}
