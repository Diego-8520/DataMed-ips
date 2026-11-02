package com.clinica.medica.domain.ports.output;

import com.clinica.medica.domain.model.Eps;
import java.util.List;
import java.util.Optional;

public interface EpsRepositoryPort {
    Eps guardar(Eps eps);
    List<Eps> listarActivas();
    Optional<Eps> buscarPorId(Long id);
}
