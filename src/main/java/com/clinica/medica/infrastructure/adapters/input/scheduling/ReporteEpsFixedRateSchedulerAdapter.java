package com.clinica.medica.infrastructure.adapters.input.scheduling;

import com.clinica.medica.application.model.ResumenEpsActivas;
import com.clinica.medica.application.ports.input.GenerarResumenEpsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "mediamed_ips.scheduler",
        name = "mode",
        havingValue = "fixed-rate")
public class ReporteEpsFixedRateSchedulerAdapter {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ReporteEpsFixedRateSchedulerAdapter.class);

    private final GenerarResumenEpsUseCase generarResumenEps;

    public ReporteEpsFixedRateSchedulerAdapter(GenerarResumenEpsUseCase generarResumenEps) {
        this.generarResumenEps = generarResumenEps;
    }

    @Scheduled(fixedRateString = "${mediamed_ips.scheduler.fixed-rate-ms:60000}")
    public void generarReporteConFixedRate() {
        ResumenEpsActivas resumen = generarResumenEps.generar();
        LOGGER.info("Resumen de EPS activas: total={}, generadoEn={}",
                resumen.total(), resumen.generadoEn());
    }
}
