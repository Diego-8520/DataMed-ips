package com.clinica.medica.application.services;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.output.EventoRegistroPuerto;
import com.clinica.medica.application.ports.output.UsuarioRepositoryPort;
import com.clinica.medica.domain.exceptions.ReglaNegocioException;
import com.clinica.medica.domain.model.Role;
import com.clinica.medica.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private EventoRegistroPuerto eventoRegistroPuerto;

    @InjectMocks
    private UsuarioService service;

    @Test
    void registrarGuardaUsuarioPacienteYPublicaEventoSinContrasena() {
        Usuario guardado = new Usuario(7L, "persona@datamed.test", "hash", Role.PACIENTE, true);
        when(usuarioRepository.existePorEmail(guardado.email())).thenReturn(false);
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(guardado);

        assertEquals(guardado, service.registrar(guardado.email(), guardado.password()));

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).guardar(usuarioCaptor.capture());
        assertEquals(Role.PACIENTE, usuarioCaptor.getValue().rol());
        assertEquals("hash", usuarioCaptor.getValue().password());

        ArgumentCaptor<RegistroCreadoEvento> eventoCaptor =
                ArgumentCaptor.forClass(RegistroCreadoEvento.class);
        verify(eventoRegistroPuerto).publicar(eventoCaptor.capture());
        assertEquals(RegistroCreadoEvento.TipoRegistro.USUARIO, eventoCaptor.getValue().tipo());
        assertEquals(guardado.id(), eventoCaptor.getValue().id());
        assertEquals(guardado.email(), eventoCaptor.getValue().identificador());
        assertNull(eventoCaptor.getValue().nombre());
    }

    @Test
    void registrarCorreoExistenteLanzaExcepcionSinGuardarNiPublicar() {
        when(usuarioRepository.existePorEmail("persona@datamed.test")).thenReturn(true);

        assertThrows(ReglaNegocioException.class,
                () -> service.registrar("persona@datamed.test", "hash"));

        verify(usuarioRepository, never()).guardar(any());
        verify(eventoRegistroPuerto, never()).publicar(any());
    }
}
