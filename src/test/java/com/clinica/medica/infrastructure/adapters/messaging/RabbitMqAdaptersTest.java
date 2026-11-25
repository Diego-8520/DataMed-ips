package com.clinica.medica.infrastructure.adapters.messaging;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.input.ProcesarEventoRegistroUseCase;
import com.clinica.medica.infrastructure.adapters.input.messaging.RabbitMqEventoRegistroListener;
import com.clinica.medica.infrastructure.adapters.output.messaging.RabbitMqEventoRegistroAdapter;
import com.clinica.medica.infrastructure.config.RabbitMqConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.Instant;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMqAdaptersTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ProcesarEventoRegistroUseCase procesarEventoRegistro;

    @Test
    void publicarUsuarioEnrutaMensajeAlExchange() {
        RegistroCreadoEvento evento = new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.USUARIO,
                7L,
                "persona@datamed.test",
                null,
                Instant.now());

        new RabbitMqEventoRegistroAdapter(rabbitTemplate).publicar(evento);

        verify(rabbitTemplate).convertAndSend(
                RabbitMqConfig.EVENTOS_EXCHANGE,
                RabbitMqConfig.USUARIO_REGISTRADO_KEY,
                evento);
    }

    @Test
    void publicarEpsUsaSuRoutingKey() {
        RegistroCreadoEvento evento = new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.EPS,
                3L,
                "900123456",
                "EPS Salud",
                Instant.now());

        new RabbitMqEventoRegistroAdapter(rabbitTemplate).publicar(evento);

        verify(rabbitTemplate).convertAndSend(
                RabbitMqConfig.EVENTOS_EXCHANGE,
                RabbitMqConfig.EPS_REGISTRADA_KEY,
                evento);
    }

    @Test
    void listenerDelegaEventoAlCasoDeUso() {
        RegistroCreadoEvento evento = new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.EPS,
                3L,
                "900123456",
                "EPS Salud",
                Instant.now());

        new RabbitMqEventoRegistroListener(procesarEventoRegistro).recibir(evento);

        verify(procesarEventoRegistro).procesar(evento);
    }
}
