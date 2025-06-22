package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Contactos;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Contacto;
import com.jofaze.backendjofaze.repository.ContactosRepository;
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
public class ContactosServiceTest {

    @Autowired
    private ContactosService contactosService;

    @MockBean
    private ContactosRepository contactosRepository;

    private Contactos createContactos() {
        Contactos contactos = new Contactos();
        contactos.setId(1L);
        contactos.setEstablecimiento(new Establecimiento());
        contactos.setContacto(new Contacto());
        return contactos;
    }

    @Test
    public void testFindAll() {
        Mockito.when(contactosRepository.findAll()).thenReturn(List.of(createContactos()));
        List<Contactos> lista = contactosService.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(contactosRepository.findById(1L)).thenReturn(Optional.of(createContactos()));
        Optional<Contactos> contactos = contactosService.findById(1L);
        assertTrue(contactos.isPresent());
        assertNotNull(contactos.get().getEstablecimiento());
    }

    @Test
    public void testSave() {
        Contactos contactos = createContactos();
        Mockito.when(contactosRepository.save(contactos)).thenReturn(contactos);
        Contactos saved = contactosService.save(contactos);
        assertNotNull(saved);
        assertNotNull(saved.getEstablecimiento());
    }

    @Test
    public void testActualizarContactos() {
        Contactos existente = createContactos();
        Contactos nuevo = createContactos();
        nuevo.setEstablecimiento(new Establecimiento());
        Mockito.when(contactosRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(contactosRepository.save(ArgumentMatchers.any(Contactos.class))).thenReturn(nuevo);

        Contactos actualizado = contactosService.actualizarContactos(1L, nuevo);
        assertNotNull(actualizado);
        assertNotNull(actualizado.getEstablecimiento());
    }

    @Test
    public void testPatchContactos() {
        Contactos existente = createContactos();
        Contactos patch = new Contactos();
        patch.setContacto(new Contacto());

        Mockito.when(contactosRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(contactosRepository.save(ArgumentMatchers.any(Contactos.class))).thenReturn(existente);

        Contactos actualizado = contactosService.patchContactos(1L, patch);
        assertNotNull(actualizado);
        assertNotNull(actualizado.getContacto());
    }
}