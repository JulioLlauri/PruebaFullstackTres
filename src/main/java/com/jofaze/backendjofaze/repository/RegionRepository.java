package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByNombre(String nombre);


    @Query("SELECT r FROM Region r WHERE LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Region> buscarPorNombreConteniendo(@Param("nombre") String nombre);
}