package com.clinica.medica.application.services;

import com.clinica.medica.application.model.ResumenEpsActivas;
import com.clinica.medica.application.ports.input.GenerarResumenEpsUseCase;
import com.clinica.medica.application.ports.output.EpsRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GenerarResumenEpsService implements GenerarResumenEpsUseCase {
    private final EpsRepositoryPort epsRepository;

    public GenerarResumenEpsService(EpsRepositoryPort epsRepository) {
        this.epsRepository = epsRepository;
    }

    @Override
    public ResumenEpsActivas generar() {
        return new ResumenEpsActivas(epsRepository.contarActivas(), Instant.now());
    }
}
