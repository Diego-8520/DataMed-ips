package com.clinica.medica.infrastructure.adapters.input.rest;

import com.clinica.medica.application.ports.input.RegistrarUsuarioUseCase;
import com.clinica.medica.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrarUsuarioUseCase registrarUsuario;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void loginValidoDevuelve200YToken() throws Exception {
        UserDetails user = usuarioAutenticado();
        when(userDetailsService.loadUserByUsername("persona@datamed.test")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-test-token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"persona@datamed.test","password":"secreto"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-test-token"));

        verify(authenticationManager).authenticate(any());
    }

    @Test
    void registroValidoDevuelve201YToken() throws Exception {
        UserDetails user = usuarioAutenticado();
        when(passwordEncoder.encode("secreto")).thenReturn("hash-seguro");
        when(userDetailsService.loadUserByUsername("persona@datamed.test")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-test-token");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"persona@datamed.test","password":"secreto"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwt-test-token"));

        verify(registrarUsuario).registrar("persona@datamed.test", "hash-seguro");
    }

    @Test
    void registroConDatosInvalidosDevuelve400() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"correo-invalido","password":""}
                                """))
                .andExpect(status().isBadRequest());
    }

    private UserDetails usuarioAutenticado() {
        return User.withUsername("persona@datamed.test")
                .password("hash-seguro")
                .authorities("ROLE_PACIENTE")
                .build();
    }
}
