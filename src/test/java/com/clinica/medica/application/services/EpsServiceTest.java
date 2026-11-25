package com.clinica.medica.application.services;

import com.clinica.medica.application.model.RegistroCreadoEvento;
import com.clinica.medica.application.ports.output.EpsRepositoryPort;
import com.clinica.medica.application.ports.output.EventoRegistroPuerto;
import com.clinica.medica.domain.exceptions.RecursoNoEncontradoException;
import com.clinica.medica.domain.model.Eps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpsServiceTest {
    @Mock
    private EpsRepositoryPort repository;

    @Mock
    private EventoRegistroPuerto eventoRegistroPuerto;

    @InjectMocks
    private EpsService service;

    @Test
    void crearGuardaEpsActivaYPublicaEvento() {
        Eps solicitada = new Eps(null, "900123456", "EPS Salud", null);
        Eps guardada = new Eps(12L, "900123456", "EPS Salud", true);
        when(repository.guardar(any(Eps.class))).thenReturn(guardada);

        Eps resultado = service.crear(solicitada);

        assertEquals(guardada, resultado);
        ArgumentCaptor<Eps> epsCaptor = ArgumentCaptor.forClass(Eps.class);
        verify(repository).guardar(epsCaptor.capture());
        assertEquals(new Eps(null, "900123456", "EPS Salud", true), epsCaptor.getValue());

        ArgumentCaptor<RegistroCreadoEvento> eventoCaptor =
                ArgumentCaptor.forClass(RegistroCreadoEvento.class);
        verify(eventoRegistroPuerto).publicar(eventoCaptor.capture());
        assertEquals(RegistroCreadoEvento.TipoRegistro.EPS, eventoCaptor.getValue().tipo());
        assertEquals(12L, eventoCaptor.getValue().id());
    }

    @Test
    void listarActivasDevuelveRepositorio() {
        List<Eps> activas = List.of(new Eps(1L, "900123456", "EPS Salud", true));
        when(repository.listarActivas()).thenReturn(activas);

        assertEquals(activas, service.listarActivas());
    }

    @Test
    void obtenerDevuelveEpsExistente() {
        Eps eps = new Eps(12L, "900123456", "EPS Salud", true);
        when(repository.buscarPorId(12L)).thenReturn(Optional.of(eps));

        assertEquals(eps, service.obtener(12L));
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.buscarPorId(12L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtener(12L));
    }

    @Test
    void actualizarGuardaLosDatosYNoPublicaEventoDeCreacion() {
        Eps eps = new Eps(12L, "900123456", "EPS Nueva", true);
        when(repository.buscarPorId(12L)).thenReturn(Optional.of(eps));
        when(repository.guardar(any(Eps.class))).thenReturn(eps);

        assertEquals(eps, service.actualizar(12L, eps));

        verify(repository).guardar(new Eps(12L, "900123456", "EPS Nueva", true));
        verify(eventoRegistroPuerto, never()).publicar(any());
    }

    @Test
    void eliminarDesactivaEpsExistente() {
        Eps eps = new Eps(12L, "900123456", "EPS Salud", true);
        when(repository.buscarPorId(12L)).thenReturn(Optional.of(eps));

        service.eliminar(12L);

        verify(repository).guardar(new Eps(12L, "900123456", "EPS Salud", false));
    }
}
