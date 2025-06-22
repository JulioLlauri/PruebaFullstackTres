package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.model.TipoEstablecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {

 
    List<Establecimiento> findByNombre(String nombre);

    List<Establecimiento> findByComuna(Comuna comuna);

    List<Establecimiento> findByTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento);

  
    @Query("SELECT e FROM Establecimiento e WHERE e.nombre = :nombre AND e.comuna = :comuna")
    List<Establecimiento> buscarPorNombreYComuna(@Param("nombre") String nombre, @Param("comuna") Comuna comuna);

    @Query("SELECT e FROM Establecimiento e WHERE e.tipoEstablecimiento = :tipoEstablecimiento AND e.comuna = :comuna")
    List<Establecimiento> buscarPorTipoYComuna(@Param("tipoEstablecimiento") TipoEstablecimiento tipoEstablecimiento, @Param("comuna") Comuna comuna);

    Optional<Establecimiento> findByEmail(String email);

    @Query("SELECT e FROM Establecimiento e JOIN e.comuna c JOIN c.region r JOIN e.tipoEstablecimiento t WHERE r.nombre = :regionNombre AND t.descripcion = :tipoDescripcion AND c.nombre = :comunaNombre AND e.nombre = :establecimientoNombre")
    List<Establecimiento> buscarPorRegionTipoComunaYNombre(
        @Param("regionNombre") String regionNombre,
        @Param("tipoDescripcion") String tipoDescripcion,
        @Param("comunaNombre") String comunaNombre,
        @Param("establecimientoNombre") String establecimientoNombre
    );
}