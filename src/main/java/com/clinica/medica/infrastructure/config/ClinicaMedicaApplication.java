package com.clinica.medica.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.clinica.medica.infrastructure.adapters.output.persistence")
@EntityScan("com.clinica.medica.infrastructure.adapters.output.persistence")
@ComponentScan("com.clinica.medica")
public class ClinicaMedicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaMedicaApplication.class, args);
    }

}
