package com.clinica.medica.application.services;

import com.clinica.medica.application.model.ResumenEpsActivas;
import com.clinica.medica.application.ports.output.EpsRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarResumenEpsServiceTest {
    @Mock
    private EpsRepositoryPort epsRepository;

    @InjectMocks
    private GenerarResumenEpsService service;

    @Test
    void generarDevuelveConteoYFechaDeGeneracion() {
        when(epsRepository.contarActivas()).thenReturn(4L);

        ResumenEpsActivas resumen = service.generar();

        assertEquals(4L, resumen.total());
        assertNotNull(resumen.generadoEn());
        verify(epsRepository).contarActivas();
    }
}
