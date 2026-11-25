package com.clinica.medica.application.ports.output;

import com.clinica.medica.application.model.RegistroCreadoEvento;

public interface EventoRegistroPuerto {
    void publicar(RegistroCreadoEvento evento);
}
