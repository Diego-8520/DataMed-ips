package com.clinica.medica.infrastructure.adapters.output.messaging;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.output.EventoRegistroPuerto;
import com.clinica.medica.infrastructure.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqEventoRegistroAdapter implements EventoRegistroPuerto {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqEventoRegistroAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicar(RegistroCreadoEvento evento) {
        String routingKey = switch (evento.tipo()) {
            case USUARIO -> RabbitMqConfig.USUARIO_REGISTRADO_KEY;
            case EPS -> RabbitMqConfig.EPS_REGISTRADA_KEY;
        };
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EVENTOS_EXCHANGE,
                routingKey,
                evento);
    }
}
