package com.clinica.medica.application.ports.output;

import com.clinica.medica.domain.model.Usuario;

public interface UsuarioRepositoryPort {
    boolean existePorEmail(String email);
    Usuario guardar(Usuario usuario);
}
