package com.clinica.medica.application.services;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.input.RegistrarUsuarioUseCase;
import com.clinica.medica.application.ports.output.EventoRegistroPuerto;
import com.clinica.medica.application.ports.output.UsuarioRepositoryPort;
import com.clinica.medica.domain.exceptions.ReglaNegocioException;
import com.clinica.medica.domain.model.Role;
import com.clinica.medica.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UsuarioService implements RegistrarUsuarioUseCase {
    private final UsuarioRepositoryPort usuarioRepository;
    private final EventoRegistroPuerto eventoRegistroPuerto;

    public UsuarioService(
            UsuarioRepositoryPort usuarioRepository,
            EventoRegistroPuerto eventoRegistroPuerto) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRegistroPuerto = eventoRegistroPuerto;
    }

    @Override
    public Usuario registrar(String email, String passwordHash) {
        if (usuarioRepository.existePorEmail(email)) {
            throw new ReglaNegocioException("El correo ya está registrado");
        }

        Usuario usuario = usuarioRepository.guardar(
                new Usuario(null, email, passwordHash, Role.PACIENTE, true));

        eventoRegistroPuerto.publicar(new RegistroCreadoEvento(
                RegistroCreadoEvento.TipoRegistro.USUARIO,
                usuario.id(),
                usuario.email(),
                null,
                Instant.now()));
        return usuario;
    }
}
