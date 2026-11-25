package com.clinica.medica.application.services;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProcesarEventoRegistroServiceTest {
    private final ProcesarEventoRegistroService service = new ProcesarEventoRegistroService();

    @Test
    void procesarAceptaEventoDeRegistro() {
        RegistroCreadoEvento evento = new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.EPS,
                3L,
                "900123456",
                "EPS Salud",
                Instant.now());

        assertDoesNotThrow(() -> service.procesar(evento));
    }
}
