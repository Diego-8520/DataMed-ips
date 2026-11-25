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
        havingValue = "cron",
        matchIfMissing = true)
public class ReporteEpsSchedulerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteEpsSchedulerAdapter.class);

    private final GenerarResumenEpsUseCase generarResumenEps;

    public ReporteEpsSchedulerAdapter(GenerarResumenEpsUseCase generarResumenEps) {
        this.generarResumenEps = generarResumenEps;
    }

    @Scheduled(cron = "${mediamed_ips.cron.expression}")
    public void generarReporteProgramado() {
        ResumenEpsActivas resumen = generarResumenEps.generar();
        LOGGER.info("Resumen de EPS activas: total={}, generadoEn={}",
                resumen.total(), resumen.generadoEn());
    }
}
