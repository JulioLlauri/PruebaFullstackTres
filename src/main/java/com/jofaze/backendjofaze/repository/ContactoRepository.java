package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    
    List<Contacto> findByTipo(String tipo);

    List<Contacto> findByNombreContacto(String nombreContacto);

    
    @Query("SELECT c FROM Contacto c WHERE c.tipo = :tipo AND c.nombreContacto = :nombreContacto")
    List<Contacto> buscarPorTipoYNombre(@Param("tipo") String tipo, @Param("nombreContacto") String nombreContacto);
}