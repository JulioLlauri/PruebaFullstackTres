package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Administrador;
import com.jofaze.backendjofaze.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void deleteById(Long id) {
        administradorRepository.deleteById(id);
    }

    // PUT: Actualización completa
    public Administrador actualizarAdministrador(Long id, Administrador administrador) {
        Administrador existente = administradorRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(administrador.getNombre());
            existente.setCargo(administrador.getCargo());
            existente.setEmail(administrador.getEmail());
            existente.setTelefono(administrador.getTelefono());
            existente.setEstablecimiento(administrador.getEstablecimiento());
            return administradorRepository.save(existente);
        } else {
            return null;
        }
    }

    // PATCH: Actualización parcial
    public Administrador patchAdministrador(Long id, Administrador administrador) {
        Administrador existente = administradorRepository.findById(id).orElse(null);
        if (existente != null) {
            if (administrador.getNombre() != null) existente.setNombre(administrador.getNombre());
            if (administrador.getCargo() != null) existente.setCargo(administrador.getCargo());
            if (administrador.getEmail() != null) existente.setEmail(administrador.getEmail());
            if (administrador.getTelefono() != null) existente.setTelefono(administrador.getTelefono());
            if (administrador.getEstablecimiento() != null) existente.setEstablecimiento(administrador.getEstablecimiento());
            return administradorRepository.save(existente);
        } else {
            return null;
        }
    }
}