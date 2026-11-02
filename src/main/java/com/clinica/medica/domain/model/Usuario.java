package com.clinica.medica.domain.model;

public record Usuario(Long id, String email, String password, Role rol, Boolean activo) {
    public Usuario {
        activo = activo == null || activo;
    }
}
