package com.clinica.medica.infrastructure.adapters.output.persistence;

import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.domain.ports.output.EpsRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class EpsPersistenceAdapter implements EpsRepositoryPort {
    private final EpsJpaRepository repository;

    public EpsPersistenceAdapter(EpsJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Eps guardar(Eps eps) {
        return toDomain(repository.save(toEntity(eps)));
    }

    @Override
    public List<Eps> listarActivas() {
        return repository.findAllByActivoTrue().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Eps> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private EpsEntity toEntity(Eps eps) {
        return new EpsEntity(eps.id(), eps.nit(), eps.nombre(), eps.activo());
    }

    private Eps toDomain(EpsEntity entity) {
        return new Eps(entity.getId(), entity.getNit(), entity.getNombre(), entity.getActivo());
    }
}
