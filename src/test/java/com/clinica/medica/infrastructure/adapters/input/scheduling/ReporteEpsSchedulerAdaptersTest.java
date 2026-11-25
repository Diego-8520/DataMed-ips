package com.clinica.medica.infrastructure.adapters.input.scheduling;

import com.clinica.medica.application.model.ResumenEpsActivas;
import com.clinica.medica.application.ports.input.GenerarResumenEpsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteEpsSchedulerAdaptersTest {
    @Mock
    private GenerarResumenEpsUseCase generarResumenEps;

    @Test
    void schedulerCronInvocaCasoDeUso() {
        when(generarResumenEps.generar()).thenReturn(new ResumenEpsActivas(2, Instant.now()));

        new ReporteEpsSchedulerAdapter(generarResumenEps).generarReporteProgramado();

        verify(generarResumenEps).generar();
    }

    @Test
    void schedulerFixedDelayInvocaCasoDeUso() {
        when(generarResumenEps.generar()).thenReturn(new ResumenEpsActivas(2, Instant.now()));

        new ReporteEpsFixedDelaySchedulerAdapter(generarResumenEps).generarReporteConFixedDelay();

        verify(generarResumenEps).generar();
    }

    @Test
    void schedulerFixedRateInvocaCasoDeUso() {
        when(generarResumenEps.generar()).thenReturn(new ResumenEpsActivas(2, Instant.now()));

        new ReporteEpsFixedRateSchedulerAdapter(generarResumenEps).generarReporteConFixedRate();

        verify(generarResumenEps).generar();
    }
}
