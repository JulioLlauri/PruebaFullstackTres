package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import com.jofaze.backendjofaze.repository.TipoEstablecimientoRepository;

@Service
public class TipoEstablecimientoService {

    @Autowired
    private TipoEstablecimientoRepository tipoEstablecimientoRepository;

    public List<TipoEstablecimiento> findAll() {
        return tipoEstablecimientoRepository.findAll();
    }

    public Optional<TipoEstablecimiento> findById(Long id) {
        return tipoEstablecimientoRepository.findById(id);
    }

    public TipoEstablecimiento save(TipoEstablecimiento tipoEstablecimiento) {
        return tipoEstablecimientoRepository.save(tipoEstablecimiento);
    }

    public void deleteById(Long id) {
        tipoEstablecimientoRepository.deleteById(id);
    }

    public TipoEstablecimiento actualizarTipoEstablecimiento(Long id, TipoEstablecimiento tipoEstablecimiento) {
        TipoEstablecimiento existente = tipoEstablecimientoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setDescripcion(tipoEstablecimiento.getDescripcion());
            return tipoEstablecimientoRepository.save(existente);
        } else {
            return null;
        }
    }

    public TipoEstablecimiento patchTipoEstablecimiento(Long id, TipoEstablecimiento tipoEstablecimiento) {
        TipoEstablecimiento existente = tipoEstablecimientoRepository.findById(id).orElse(null);
        if (existente != null) {
            if (tipoEstablecimiento.getDescripcion() != null) existente.setDescripcion(tipoEstablecimiento.getDescripcion());
            return tipoEstablecimientoRepository.save(existente);
        } else {
            return null;
        }
    }
}