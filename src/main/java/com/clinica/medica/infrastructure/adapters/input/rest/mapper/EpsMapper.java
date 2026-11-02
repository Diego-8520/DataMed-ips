package com.clinica.medica.infrastructure.adapters.input.rest.mapper;

import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsRequest;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsResponse;

public final class EpsMapper {
    private EpsMapper() {}
    public static Eps toDomain(EpsRequest request) {
        return new Eps(null, request.nit(), request.nombre(), true);
    }
    public static Eps toDomain(Long id, EpsRequest request) {
        return new Eps(id, request.nit(), request.nombre(), true);
    }
    public static EpsResponse toResponse(Eps eps) {
        return new EpsResponse(eps.id(), eps.nit(), eps.nombre(), eps.activo());
    }
}
