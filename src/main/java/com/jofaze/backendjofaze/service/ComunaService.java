package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.repository.ComunaRepository;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> findById(Long id) {
        return comunaRepository.findById(id);
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteById(Long id) {
        comunaRepository.deleteById(id);
    }

    public Comuna actualizarComuna(Long id, Comuna comuna) {
        Comuna existente = comunaRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(comuna.getNombre());
            existente.setRegion(comuna.getRegion());
            return comunaRepository.save(existente);
        } else {
            return null;
        }
    }

    public Comuna patchComuna(Long id, Comuna comuna) {
        Comuna existente = comunaRepository.findById(id).orElse(null);
        if (existente != null) {
            if (comuna.getNombre() != null) existente.setNombre(comuna.getNombre());
            if (comuna.getRegion() != null) existente.setRegion(comuna.getRegion());
            return comunaRepository.save(existente);
        } else {
            return null;
        }
    }
}