package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Administrador;

import com.jofaze.backendjofaze.model.Establecimiento;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

   
    List<Administrador> findByNombre(String nombre);

    Optional<Administrador> findByEmail(String email);

    List<Administrador> findByNombreAndCargo(String nombre, String cargo);
    List<Administrador> findByEstablecimiento(Establecimiento establecimiento);


   
    @Query("SELECT a FROM Administrador a WHERE a.nombre = :nombre")
    List<Administrador> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Administrador a WHERE a.email = :email")
    Optional<Administrador> buscarPorEmail(@Param("email") String email);

    @Query("SELECT a FROM Administrador a WHERE a.nombre = :nombre AND a.cargo = :cargo")
    List<Administrador> buscarPorNombreYCargo(@Param("nombre") String nombre, @Param("cargo") String cargo);
}