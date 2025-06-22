package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Habitacion;
import com.jofaze.backendjofaze.model.Administrador;
import com.jofaze.backendjofaze.model.Contactos;
import com.jofaze.backendjofaze.repository.EstablecimientoRepository;
import com.jofaze.backendjofaze.repository.HabitacionRepository;
import com.jofaze.backendjofaze.repository.AdministradorRepository;
import com.jofaze.backendjofaze.repository.ContactosRepository;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;
    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private ContactosRepository contactosRepository;

    public List<Establecimiento> findAll() {
        return establecimientoRepository.findAll();
    }

    public Optional<Establecimiento> findById(Long id) {
        return establecimientoRepository.findById(id);
    }

    public Establecimiento save(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

    public void deleteById(Long id) {
        Establecimiento est = establecimientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));

        // Eliminar habitaciones asociadas
        List<Habitacion> habitaciones = habitacionRepository.findByEstablecimiento(est);
        for (Habitacion h : habitaciones) {
            habitacionRepository.delete(h);
        }

        // Eliminar administradores asociados
        List<Administrador> admins = administradorRepository.findByEstablecimiento(est);
        for (Administrador a : admins) {
            administradorRepository.delete(a);
        }

        // Eliminar contactos asociados
        List<Contactos> contactos = contactosRepository.findByEstablecimiento(est);
        for (Contactos c : contactos) {
            contactosRepository.delete(c);
        }

        // Finalmente, eliminar el establecimiento
        establecimientoRepository.delete(est);
    }

    public Establecimiento actualizarEstablecimiento(Long id, Establecimiento establecimiento) {
        Establecimiento existente = establecimientoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(establecimiento.getNombre());
            existente.setDireccion(establecimiento.getDireccion());
            existente.setTelefono(establecimiento.getTelefono());
            existente.setEmail(establecimiento.getEmail());
            existente.setComuna(establecimiento.getComuna());
            existente.setTipoEstablecimiento(establecimiento.getTipoEstablecimiento());
            return establecimientoRepository.save(existente);
        } else {
            return null;
        }
    }

    public Establecimiento patchEstablecimiento(Long id, Establecimiento establecimiento) {
        Establecimiento existente = establecimientoRepository.findById(id).orElse(null);
        if (existente != null) {
            if (establecimiento.getNombre() != null) existente.setNombre(establecimiento.getNombre());
            if (establecimiento.getDireccion() != null) existente.setDireccion(establecimiento.getDireccion());
            if (establecimiento.getTelefono() != null) existente.setTelefono(establecimiento.getTelefono());
            if (establecimiento.getEmail() != null) existente.setEmail(establecimiento.getEmail());
            if (establecimiento.getComuna() != null) existente.setComuna(establecimiento.getComuna());
            if (establecimiento.getTipoEstablecimiento() != null) existente.setTipoEstablecimiento(establecimiento.getTipoEstablecimiento());
            return establecimientoRepository.save(existente);
        } else {
            return null;
        }
    }
}