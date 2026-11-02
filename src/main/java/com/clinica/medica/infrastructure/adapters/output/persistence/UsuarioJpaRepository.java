package com.clinica.medica.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmailAndActivoTrue(String email);
    boolean existsByEmail(String email);
}
