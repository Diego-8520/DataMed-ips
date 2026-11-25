package com.clinica.medica.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {
    private static final String SECRET = "a-test-secret-key-that-is-at-least-32-bytes-long";

    @Test
    void tokenGeneradoEsValidoParaElUsuarioYPermiteExtraerEmail() {
        JwtService service = new JwtService(SECRET, 60_000);
        UserDetails user = usuario("persona@datamed.test");

        String token = service.generateToken(user);

        assertEquals(user.getUsername(), service.extractUsername(token));
        assertTrue(service.isTokenValid(token, user));
        assertFalse(service.isTokenValid(token, usuario("otro@datamed.test")));
    }

    @Test
    void tokenExpiradoNoEsValido() {
        JwtService service = new JwtService(SECRET, 0);
        UserDetails user = usuario("persona@datamed.test");

        String token = service.generateToken(user);

        assertFalse(service.isTokenValid(token, user));
    }

    @Test
    void aceptaClaveCodificadaEnBase64() {
        String secretBase64 = Base64.getEncoder()
                .encodeToString(SECRET.getBytes(StandardCharsets.UTF_8));
        JwtService service = new JwtService(secretBase64, 60_000);
        UserDetails user = usuario("persona@datamed.test");

        assertTrue(service.isTokenValid(service.generateToken(user), user));
    }

    private UserDetails usuario(String username) {
        return User.withUsername(username)
                .password("hash")
                .authorities("ROLE_PACIENTE")
                .build();
    }
}
