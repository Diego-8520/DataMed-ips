package com.clinica.medica.domain.ports.input;

import com.clinica.medica.domain.model.Eps;
import java.util.List;

public interface EpsUseCase {
    Eps crear(Eps eps);
    List<Eps> listarActivas();
    Eps obtener(Long id);
    Eps actualizar(Long id, Eps eps);
    void eliminar(Long id);
}
