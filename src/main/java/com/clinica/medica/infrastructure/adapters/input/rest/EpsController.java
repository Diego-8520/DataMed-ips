package com.clinica.medica.infrastructure.adapters.input.rest;

import com.clinica.medica.domain.ports.input.EpsUseCase;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsRequest;
import com.clinica.medica.infrastructure.adapters.input.rest.dto.EpsResponse;
import com.clinica.medica.infrastructure.adapters.input.rest.mapper.EpsMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/eps")
@Tag(name = "EPS", description = "Gestión de entidades promotoras de salud")
public class EpsController {
    private final EpsUseCase useCase;

    public EpsController(EpsUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear EPS")
    @ApiResponse(responseCode = "201", description = "EPS creada")
    public EpsResponse crear(@Valid @RequestBody EpsRequest request) {
        return EpsMapper.toResponse(useCase.crear(EpsMapper.toDomain(request)));
    }

    @GetMapping
    @Operation(summary = "Listar EPS activas")
    public List<EpsResponse> listar() {
        return useCase.listarActivas().stream().map(EpsMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener EPS por ID")
    public EpsResponse obtener(@PathVariable Long id) {
        return EpsMapper.toResponse(useCase.obtener(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar EPS")
    public EpsResponse actualizar(@PathVariable Long id, @Valid @RequestBody EpsRequest request) {
        return EpsMapper.toResponse(useCase.actualizar(id, EpsMapper.toDomain(id, request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Desactivar EPS")
    @ApiResponse(responseCode = "204", description = "EPS desactivada lógicamente")
    public void eliminar(@PathVariable Long id) {
        useCase.eliminar(id);
    }
}
