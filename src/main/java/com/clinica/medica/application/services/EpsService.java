package com.clinica.medica.application.services;

import com.clinica.medica.domain.exceptions.RecursoNoEncontradoException;
import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.domain.ports.input.EpsUseCase;
import com.clinica.medica.domain.ports.output.EpsRepositoryPort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EpsService implements EpsUseCase {
    private final EpsRepositoryPort repository;

    public EpsService(EpsRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @CacheEvict(value = "eps_cache", allEntries = true)
    public Eps crear(Eps eps) {
        return repository.guardar(new Eps(null, eps.nit(), eps.nombre(), true));
    }

    @Override
    @Cacheable("eps_cache")
    public List<Eps> listarActivas() {
        return repository.listarActivas();
    }

    @Override
    public Eps obtener(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("EPS no encontrada: " + id));
    }

    @Override
    @CacheEvict(value = "eps_cache", allEntries = true)
    public Eps actualizar(Long id, Eps eps) {
        obtener(id);
        return repository.guardar(new Eps(id, eps.nit(), eps.nombre(), true));
    }

    @Override
    @CacheEvict(value = "eps_cache", allEntries = true)
    public void eliminar(Long id) {
        Eps actual = obtener(id);
        repository.guardar(new Eps(actual.id(), actual.nit(), actual.nombre(), false));
    }
}
