package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Habitacion;
import com.jofaze.backendjofaze.repository.HabitacionRepository;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitacion> findById(Long id) {
        return habitacionRepository.findById(id);
    }

    public Habitacion save(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public void deleteById(Long id) {
        habitacionRepository.deleteById(id);
    }

    public Habitacion actualizarHabitacion(Long id, Habitacion habitacion) {
        Habitacion existente = habitacionRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNumero(habitacion.getNumero());
            existente.setTipo(habitacion.getTipo());
            existente.setCapacidad(habitacion.getCapacidad());
            existente.setEstablecimiento(habitacion.getEstablecimiento());
            existente.setCliente(habitacion.getCliente());
            return habitacionRepository.save(existente);
        } else {
            return null;
        }
    }

    public Habitacion patchHabitacion(Long id, Habitacion habitacion) {
        Habitacion existente = habitacionRepository.findById(id).orElse(null);
        if (existente != null) {
            if (habitacion.getNumero() != null) existente.setNumero(habitacion.getNumero());
            if (habitacion.getTipo() != null) existente.setTipo(habitacion.getTipo());
            if (habitacion.getCapacidad() != null) existente.setCapacidad(habitacion.getCapacidad());
            if (habitacion.getEstablecimiento() != null) existente.setEstablecimiento(habitacion.getEstablecimiento());
            if (habitacion.getCliente() != null) existente.setCliente(habitacion.getCliente());
            return habitacionRepository.save(existente);
        } else {
            return null;
        }
    }
}