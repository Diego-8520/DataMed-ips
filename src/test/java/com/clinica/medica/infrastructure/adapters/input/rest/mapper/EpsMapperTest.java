package com.clinica.medica.infrastructure.adapters.input.rest.mapper;

import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsRequest;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpsMapperTest {
    @Test
    void requestSeMapeaAEpsNueva() {
        assertEquals(new Eps(null, "900123456", "EPS Salud", true),
                EpsMapper.toDomain(new EpsRequest("900123456", "EPS Salud")));
    }

    @Test
    void requestSeMapeaAEpsExistente() {
        assertEquals(new Eps(4L, "900123456", "EPS Salud", true),
                EpsMapper.toDomain(4L, new EpsRequest("900123456", "EPS Salud")));
    }

    @Test
    void epsSeMapeaAResponse() {
        assertEquals(new EpsResponse(4L, "900123456", "EPS Salud", true),
                EpsMapper.toResponse(new Eps(4L, "900123456", "EPS Salud", true)));
    }
}
