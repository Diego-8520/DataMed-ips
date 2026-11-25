package com.clinica.medica.infrastructure.adapters.output.persistence;

import com.clinica.medica.domain.model.Eps;
import com.clinica.medica.infrastructure.adapters.output.persistence.entities.EpsEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpsPersistenceAdapterTest {
    @Mock
    private EpsJpaRepository repository;

    @InjectMocks
    private EpsPersistenceAdapter adapter;

    @Test
    void guardarMapeaDominioAEntidadYRespuesta() {
        Eps eps = new Eps(4L, "900123456", "EPS Salud", true);
        when(repository.save(any(EpsEntity.class)))
                .thenReturn(new EpsEntity(4L, "900123456", "EPS Salud", true));

        assertEquals(eps, adapter.guardar(eps));
    }

    @Test
    void listarActivasMapeaLasEntidades() {
        when(repository.findAllByActivoTrue()).thenReturn(
                List.of(new EpsEntity(4L, "900123456", "EPS Salud", true)));

        assertEquals(List.of(new Eps(4L, "900123456", "EPS Salud", true)),
                adapter.listarActivas());
    }

    @Test
    void contarActivasDelegaAlRepositorioJpa() {
        when(repository.countByActivoTrue()).thenReturn(2L);

        assertEquals(2L, adapter.contarActivas());
        verify(repository).countByActivoTrue();
    }

    @Test
    void buscarPorIdMapeaResultadoEncontrado() {
        when(repository.findById(4L)).thenReturn(
                Optional.of(new EpsEntity(4L, "900123456", "EPS Salud", true)));

        assertEquals(Optional.of(new Eps(4L, "900123456", "EPS Salud", true)),
                adapter.buscarPorId(4L));
    }
}
