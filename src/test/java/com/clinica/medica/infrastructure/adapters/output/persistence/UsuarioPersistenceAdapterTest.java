package com.clinica.medica.infrastructure.adapters.output.persistence;

import com.clinica.medica.domain.model.Role;
import com.clinica.medica.domain.model.Usuario;
import com.clinica.medica.infrastructure.adapters.output.persistence.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioPersistenceAdapterTest {
    @Mock
    private UsuarioJpaRepository repository;

    @InjectMocks
    private UsuarioPersistenceAdapter adapter;

    @Test
    void existePorEmailDelegaAlRepositorio() {
        when(repository.existsByEmail("persona@datamed.test")).thenReturn(true);

        assertEquals(true, adapter.existePorEmail("persona@datamed.test"));
    }

    @Test
    void guardarMapeaUsuarioEnAmbasDirecciones() {
        Usuario usuario = new Usuario(
                null, "persona@datamed.test", "hash", Role.PACIENTE, true);
        when(repository.save(any(UsuarioEntity.class)))
                .thenReturn(new UsuarioEntity(
                        9L, "persona@datamed.test", "hash", Role.PACIENTE, true));

        assertEquals(new Usuario(
                9L, "persona@datamed.test", "hash", Role.PACIENTE, true),
                adapter.guardar(usuario));
    }
}
