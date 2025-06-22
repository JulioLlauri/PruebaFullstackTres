package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComunaRepository extends JpaRepository<Comuna, Long> {

    
    Optional<Comuna> findByNombre(String nombre);

    List<Comuna> findByRegion(Region region);

    
    @Query("SELECT c FROM Comuna c WHERE c.nombre = :nombre AND c.region = :region")
    Optional<Comuna> buscarPorNombreYRegion(@Param("nombre") String nombre, @Param("region") Region region);
}