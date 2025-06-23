package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Habitacion;
import com.jofaze.backendjofaze.model.Establecimiento;

import com.jofaze.backendjofaze.model.Cliente;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {


    List<Habitacion> findByNumero(String numero);

    List<Habitacion> findByTipo(String tipo);

    List<Habitacion> findByEstablecimiento(Establecimiento establecimiento);

    List<Habitacion> findByCliente(Cliente cliente);


  


    @Query("SELECT h FROM Habitacion h WHERE h.tipo = :tipo AND h.establecimiento = :establecimiento")
    List<Habitacion> buscarPorTipoYEstablecimiento(@Param("tipo") String tipo, @Param("establecimiento") Establecimiento establecimiento);

    @Query("SELECT h FROM Habitacion h WHERE h.capacidad >= :capacidad")
    List<Habitacion> buscarPorCapacidadMinima(@Param("capacidad") Integer capacidad);

    @Query("SELECT h FROM Habitacion h JOIN h.establecimiento e JOIN e.comuna c JOIN c.region r WHERE h.tipo = :tipo AND e.nombre = :nombreEstablecimiento AND c.nombre = :nombreComuna AND r.nombre = :nombreRegion")
    List<Habitacion> buscarHabitacionesPorTipoYEstablecimientoYComunaYRegion(
        @Param("tipo") String tipo,
        @Param("nombreEstablecimiento") String nombreEstablecimiento,
        @Param("nombreComuna") String nombreComuna,
        @Param("nombreRegion") String nombreRegion
    );
}