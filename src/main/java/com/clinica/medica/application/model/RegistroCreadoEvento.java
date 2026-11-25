package com.clinica.medica.application.model;

import java.time.Instant;

public record RegistroCreadoEvento(
        TipoRegistro tipo,
        Long id,
        String identificador,
        String nombre,
        Instant creadoEn) {

    public enum TipoRegistro {
        USUARIO,
        EPS
    }
}
