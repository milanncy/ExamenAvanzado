package com.codigo.ms_caceres_anculle_hexagonal.infraestructure.dao;

import com.codigo.ms_caceres_anculle_hexagonal.infraestructure.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    Boolean existsByNumDoc(String numDoc);
    Optional<EmpleadoEntity> findByNumDoc(String numDoc);
    List<EmpleadoEntity> findByEstado(boolean estado);
    List<EmpleadoEntity> findAllByEstadoTrue();
}
