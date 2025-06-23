package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Cliente;
import com.jofaze.backendjofaze.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    private Cliente createCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Ana");
        cliente.setApellido("García");
        cliente.setTelefono("987654321");
        cliente.setEmail("ana@correo.com");
        return cliente;
    }

    @Test
    public void testFindAll() {
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(createCliente()));
        List<Cliente> clientes = clienteService.findAll();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(createCliente()));
        Optional<Cliente> cliente = clienteService.findById(1L);
        assertTrue(cliente.isPresent());
        assertEquals("Ana", cliente.get().getNombre());
    }

    @Test
    public void testSave() {
        Cliente cliente = createCliente();
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente saved = clienteService.save(cliente);
        assertNotNull(saved);
        assertEquals("Ana", saved.getNombre());
    }

    @Test
    public void testActualizarCliente() {
        Cliente existente = createCliente();
        Cliente nuevo = createCliente();
        nuevo.setNombre("Pedro");
        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(nuevo);

        Cliente actualizado = clienteService.actualizarCliente(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Pedro", actualizado.getNombre());
    }

    @Test
    public void testPatchCliente() {
        Cliente existente = createCliente();
        Cliente patch = new Cliente();
        patch.setTelefono("123123123");

        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(existente);

        Cliente actualizado = clienteService.patchCliente(1L, patch);
        assertNotNull(actualizado);
        assertEquals("123123123", actualizado.getTelefono());
    }
}