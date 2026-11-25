package com.clinica.medica.infrastructure.adapters.input.messaging;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.input.ProcesarEventoRegistroUseCase;
import com.clinica.medica.infrastructure.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqEventoRegistroListener {
    private final ProcesarEventoRegistroUseCase procesarEventoRegistro;

    public RabbitMqEventoRegistroListener(ProcesarEventoRegistroUseCase procesarEventoRegistro) {
        this.procesarEventoRegistro = procesarEventoRegistro;
    }

    @RabbitListener(queues = RabbitMqConfig.REGISTROS_QUEUE)
    public void recibir(RegistroCreadoEvento evento) {
        procesarEventoRegistro.procesar(evento);
    }
}
