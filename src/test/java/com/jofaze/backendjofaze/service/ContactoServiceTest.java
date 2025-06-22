package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Contacto;
import com.jofaze.backendjofaze.repository.ContactoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ContactoServiceTest {

    @Autowired
    private ContactoService contactoService;

    @MockBean
    private ContactoRepository contactoRepository;

    private Contacto createContacto() {
        Contacto contacto = new Contacto();
        contacto.setId(1L);
        contacto.setTipo("Email");
        contacto.setNombreContacto("contacto@hotel.com");
        return contacto;
    }

    @Test
    public void testFindAll() {
        Mockito.when(contactoRepository.findAll()).thenReturn(List.of(createContacto()));
        List<Contacto> contactos = contactoService.findAll();
        assertNotNull(contactos);
        assertEquals(1, contactos.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(contactoRepository.findById(1L)).thenReturn(Optional.of(createContacto()));
        Optional<Contacto> contacto = contactoService.findById(1L);
        assertTrue(contacto.isPresent());
        assertEquals("Email", contacto.get().getTipo());
    }

    @Test
    public void testSave() {
        Contacto contacto = createContacto();
        Mockito.when(contactoRepository.save(contacto)).thenReturn(contacto);
        Contacto saved = contactoService.save(contacto);
        assertNotNull(saved);
        assertEquals("Email", saved.getTipo());
    }

    @Test
    public void testActualizarContacto() {
        Contacto existente = createContacto();
        Contacto nuevo = createContacto();
        nuevo.setTipo("Teléfono");
        Mockito.when(contactoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(contactoRepository.save(ArgumentMatchers.any(Contacto.class))).thenReturn(nuevo);

        Contacto actualizado = contactoService.actualizarContacto(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Teléfono", actualizado.getTipo());
    }

    @Test
    public void testPatchContacto() {
        Contacto existente = createContacto();
        Contacto patch = new Contacto();
        patch.setNombreContacto("nuevo@hotel.com");

        Mockito.when(contactoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(contactoRepository.save(ArgumentMatchers.any(Contacto.class))).thenReturn(existente);

        Contacto actualizado = contactoService.patchContacto(1L, patch);
        assertNotNull(actualizado);
        assertEquals("nuevo@hotel.com", actualizado.getNombreContacto());
    }
}