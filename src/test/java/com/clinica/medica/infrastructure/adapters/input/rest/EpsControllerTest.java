package com.clinica.medica.infrastructure.adapters.input.rest;

import com.clinica.medica.application.ports.input.EpsUseCase;
import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EpsController.class)
@AutoConfigureMockMvc(addFilters = false)
class EpsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EpsUseCase useCase;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void crearEpsDevuelve201() throws Exception {
        when(useCase.crear(any(Eps.class)))
                .thenReturn(new Eps(12L, "900123456", "EPS Salud", true));

        mockMvc.perform(post("/api/v1/eps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nit":"900123456","nombre":"EPS Salud"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.nombre").value("EPS Salud"));
    }

    @Test
    void listarEpsActivasDevuelve200() throws Exception {
        when(useCase.listarActivas()).thenReturn(
                List.of(new Eps(12L, "900123456", "EPS Salud", true)));

        mockMvc.perform(get("/api/v1/eps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nit").value("900123456"));
    }

    @Test
    void obtenerEpsDevuelve200() throws Exception {
        when(useCase.obtener(12L)).thenReturn(
                new Eps(12L, "900123456", "EPS Salud", true));

        mockMvc.perform(get("/api/v1/eps/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12));
    }

    @Test
    void actualizarEpsDevuelve200() throws Exception {
        when(useCase.actualizar(any(Long.class), any(Eps.class)))
                .thenReturn(new Eps(12L, "900123456", "EPS Actualizada", true));

        mockMvc.perform(put("/api/v1/eps/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nit":"900123456","nombre":"EPS Actualizada"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("EPS Actualizada"));
    }

    @Test
    void eliminarEpsDevuelve204() throws Exception {
        mockMvc.perform(delete("/api/v1/eps/12"))
                .andExpect(status().isNoContent());

        verify(useCase).eliminar(12L);
    }
}
