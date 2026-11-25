package com.clinica.medica.application.services;

import com.clinica.medica.domain.exceptions.RecursoNoEncontradoException;
import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.input.EpsUseCase;
import com.clinica.medica.application.ports.output.EventoRegistroPuerto;
import com.clinica.medica.application.ports.output.EpsRepositoryPort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EpsService implements EpsUseCase {
    private final EpsRepositoryPort repository;
    private final EventoRegistroPuerto eventoRegistroPuerto;

    public EpsService(EpsRepositoryPort repository, EventoRegistroPuerto eventoRegistroPuerto) {
        this.repository = repository;
        this.eventoRegistroPuerto = eventoRegistroPuerto;
    }

    @Override
    @CacheEvict(value = "eps_cache", allEntries = true)
    public Eps crear(Eps eps) {
        Eps creada = repository.guardar(new Eps(null, eps.nit(), eps.nombre(), true));
        eventoRegistroPuerto.publicar(new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.EPS,
                creada.id(),
                creada.nit(),
                creada.nombre(),
                Instant.now()));
        return creada;
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
