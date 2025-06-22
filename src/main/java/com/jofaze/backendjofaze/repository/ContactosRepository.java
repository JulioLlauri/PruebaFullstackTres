package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Contactos;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactosRepository extends JpaRepository<Contactos, Long> {


    List<Contactos> findByEstablecimiento(Establecimiento establecimiento);

    List<Contactos> findByContacto(Contacto contacto);



    @Query("SELECT c FROM Contactos c WHERE c.establecimiento = :establecimiento AND c.contacto = :contacto")
    List<Contactos> buscarPorEstablecimientoYContacto(@Param("establecimiento") Establecimiento establecimiento, @Param("contacto") Contacto contacto);
}