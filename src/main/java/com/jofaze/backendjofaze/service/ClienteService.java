package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Cliente;
import com.jofaze.backendjofaze.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    // PUT: Actualización completa
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente existente = clienteRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(cliente.getNombre());
            existente.setApellido(cliente.getApellido());
            existente.setTelefono(cliente.getTelefono());
            existente.setEmail(cliente.getEmail());
            return clienteRepository.save(existente);
        } else {
            return null;
        }
    }

    // PATCH: Actualización parcial
    public Cliente patchCliente(Long id, Cliente cliente) {
        Cliente existente = clienteRepository.findById(id).orElse(null);
        if (existente != null) {
            if (cliente.getNombre() != null) existente.setNombre(cliente.getNombre());
            if (cliente.getApellido() != null) existente.setApellido(cliente.getApellido());
            if (cliente.getTelefono() != null) existente.setTelefono(cliente.getTelefono());
            if (cliente.getEmail() != null) existente.setEmail(cliente.getEmail());
            return clienteRepository.save(existente);
        } else {
            return null;
        }
    }
}