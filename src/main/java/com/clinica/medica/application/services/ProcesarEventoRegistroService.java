package com.clinica.medica.application.services;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.input.ProcesarEventoRegistroUseCase;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ProcesarEventoRegistroService implements ProcesarEventoRegistroUseCase {
    private static final Logger LOGGER =
            Logger.getLogger(ProcesarEventoRegistroService.class.getName());

    @Override
    public void procesar(RegistroCreadoEvento evento) {
        LOGGER.info(() -> "Evento de registro recibido: tipo=" + evento.tipo()
                + ", id=" + evento.id());
    }
}
