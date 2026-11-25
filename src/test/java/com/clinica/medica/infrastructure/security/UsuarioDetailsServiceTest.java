package com.clinica.medica.infrastructure.security;

import com.clinica.medica.domain.model.Role;
import com.clinica.medica.infrastructure.adapters.output.persistence.UsuarioJpaRepository;
import com.clinica.medica.infrastructure.adapters.output.persistence.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioDetailsServiceTest {
    @Mock
    private UsuarioJpaRepository repository;

    @InjectMocks
    private UsuarioDetailsService service;

    @Test
    void cargaUsuarioActivoYAsignaAutoridadDeRol() {
        when(repository.findByEmailAndActivoTrue("persona@datamed.test"))
                .thenReturn(Optional.of(new UsuarioEntity(
                        1L, "persona@datamed.test", "hash", Role.PACIENTE, true)));

        UserDetails details = service.loadUserByUsername("persona@datamed.test");

        assertEquals("persona@datamed.test", details.getUsername());
        assertEquals("hash", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_PACIENTE")));
        assertTrue(details.isEnabled());
    }

    @Test
    void usuarioNoEncontradoLanzaExcepcion() {
        when(repository.findByEmailAndActivoTrue("ausente@datamed.test"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("ausente@datamed.test"));
    }
}
