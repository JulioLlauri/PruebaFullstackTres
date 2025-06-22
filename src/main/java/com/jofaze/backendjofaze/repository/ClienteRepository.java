package com.jofaze.backendjofaze.repository;

import com.jofaze.backendjofaze.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    List<Cliente> findByNombreAndApellido(String nombre, String apellido);

    Optional<Cliente> findByEmail(String email);


    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre AND c.apellido = :apellido")
    List<Cliente> buscarPorNombreYApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    Optional<Cliente> buscarPorEmail(@Param("email") String email);
}