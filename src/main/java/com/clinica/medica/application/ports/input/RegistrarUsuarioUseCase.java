package com.clinica.medica.application.ports.input;

import com.clinica.medica.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario registrar(String email, String passwordHash);
}
