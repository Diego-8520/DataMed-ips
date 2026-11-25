package com.clinica.medica.infrastructure.adapters.output.persistence;

import com.clinica.medica.application.ports.output.UsuarioRepositoryPort;
import com.clinica.medica.domain.model.Usuario;
import com.clinica.medica.infrastructure.adapters.output.persistence.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository repository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(
                usuario.id(),
                usuario.email(),
                usuario.password(),
                usuario.rol(),
                usuario.activo());
        UsuarioEntity guardado = repository.save(entity);
        return new Usuario(
                guardado.getId(),
                guardado.getEmail(),
                guardado.getPassword(),
                guardado.getRol(),
                guardado.getActivo());
    }
}
