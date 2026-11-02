package com.clinica.medica.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EpsJpaRepository extends JpaRepository<EpsEntity, Long> {
    List<EpsEntity> findAllByActivoTrue();
}
