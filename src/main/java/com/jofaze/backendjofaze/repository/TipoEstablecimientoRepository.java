package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TipoEstablecimientoRepository extends JpaRepository<TipoEstablecimiento, Long> {


    Optional<TipoEstablecimiento> findByDescripcion(String descripcion);


    @Query("SELECT t FROM TipoEstablecimiento t WHERE LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :desc, '%'))")
    List<TipoEstablecimiento> buscarPorDescripcionConteniendo(@Param("desc") String desc);
}