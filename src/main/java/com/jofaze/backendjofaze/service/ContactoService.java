package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Contacto;
import com.jofaze.backendjofaze.repository.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    public List<Contacto> findAll() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> findById(Long id) {
        return contactoRepository.findById(id);
    }

    public Contacto save(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public void deleteById(Long id) {
        contactoRepository.deleteById(id);
    }

    public Contacto actualizarContacto(Long id, Contacto contacto) {
        Contacto existente = contactoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setTipo(contacto.getTipo());
            existente.setNombreContacto(contacto.getNombreContacto());
            return contactoRepository.save(existente);
        } else {
            return null;
        }
    }

    public Contacto patchContacto(Long id, Contacto contacto) {
        Contacto existente = contactoRepository.findById(id).orElse(null);
        if (existente != null) {
            if (contacto.getTipo() != null) existente.setTipo(contacto.getTipo());
            if (contacto.getNombreContacto() != null) existente.setNombreContacto(contacto.getNombreContacto());
            return contactoRepository.save(existente);
        } else {
            return null;
        }
    }
}