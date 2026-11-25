package com.clinica.medica.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {
    public static final String REGISTROS_QUEUE = "datamed.registros.queue";
    public static final String EVENTOS_EXCHANGE = "datamed.eventos";
    public static final String USUARIO_REGISTRADO_KEY = "datamed.usuario.registrado";
    public static final String EPS_REGISTRADA_KEY = "datamed.eps.registrada";

    @Bean
    public Queue registrosQueue() {
        return new Queue(REGISTROS_QUEUE, true);
    }

    @Bean
    public TopicExchange eventosExchange() {
        return new TopicExchange(EVENTOS_EXCHANGE, true, false);
    }

    @Bean
    public Binding usuarioRegistradoBinding(Queue registrosQueue, TopicExchange eventosExchange) {
        return BindingBuilder.bind(registrosQueue)
                .to(eventosExchange)
                .with(USUARIO_REGISTRADO_KEY);
    }

    @Bean
    public Binding epsRegistradaBinding(Queue registrosQueue, TopicExchange eventosExchange) {
        return BindingBuilder.bind(registrosQueue)
                .to(eventosExchange)
                .with(EPS_REGISTRADA_KEY);
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
