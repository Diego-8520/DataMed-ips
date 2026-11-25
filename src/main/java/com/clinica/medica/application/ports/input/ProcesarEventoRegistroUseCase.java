package com.clinica.medica.application.ports.input;

import com.clinica.medica.application.model.RegistroCreadoEvento;

public interface ProcesarEventoRegistroUseCase {
    void procesar(RegistroCreadoEvento evento);
}
