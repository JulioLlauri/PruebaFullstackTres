package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Contactos;
import com.jofaze.backendjofaze.repository.ContactosRepository;

@Service
public class ContactosService {

    @Autowired
    private ContactosRepository contactosRepository;

    public List<Contactos> findAll() {
        return contactosRepository.findAll();
    }

    public Optional<Contactos> findById(Long id) {
        return contactosRepository.findById(id);
    }

    public Contactos save(Contactos contactos) {
        return contactosRepository.save(contactos);
    }

    public void deleteById(Long id) {
        contactosRepository.deleteById(id);
    }

    public Contactos actualizarContactos(Long id, Contactos contactos) {
        Contactos existente = contactosRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEstablecimiento(contactos.getEstablecimiento());
            existente.setContacto(contactos.getContacto());
            return contactosRepository.save(existente);
        } else {
            return null;
        }
    }

    public Contactos patchContactos(Long id, Contactos contactos) {
        Contactos existente = contactosRepository.findById(id).orElse(null);
        if (existente != null) {
            if (contactos.getEstablecimiento() != null) existente.setEstablecimiento(contactos.getEstablecimiento());
            if (contactos.getContacto() != null) existente.setContacto(contactos.getContacto());
            return contactosRepository.save(existente);
        } else {
            return null;
        }
    }
}