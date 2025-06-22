package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Administrador;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.repository.AdministradorRepository;
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
public class AdministradorServiceTest {

    @Autowired
    private AdministradorService administradorService;

    @MockBean
    private AdministradorRepository administradorRepository;

    private Administrador createAdministrador() {
        Administrador admin = new Administrador();
        admin.setId(1L);
        admin.setNombre("Juan Pérez");
        admin.setCargo("Gerente");
        admin.setEmail("juan@hotel.com");
        admin.setTelefono("123456789");
        admin.setEstablecimiento(new Establecimiento());
        return admin;
    }

    @Test
    public void testFindAll() {
        Mockito.when(administradorRepository.findAll()).thenReturn(List.of(createAdministrador()));
        List<Administrador> admins = administradorService.findAll();
        assertNotNull(admins);
        assertEquals(1, admins.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(createAdministrador()));
        Optional<Administrador> admin = administradorService.findById(1L);
        assertTrue(admin.isPresent());
        assertEquals("Juan Pérez", admin.get().getNombre());
    }

    @Test
    public void testSave() {
        Administrador admin = createAdministrador();
        Mockito.when(administradorRepository.save(admin)).thenReturn(admin);
        Administrador saved = administradorService.save(admin);
        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
    }

    @Test
    public void testActualizarAdministrador() {
        Administrador existente = createAdministrador();
        Administrador nuevo = createAdministrador();
        nuevo.setNombre("Pedro Gómez");
        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(administradorRepository.save(ArgumentMatchers.any(Administrador.class))).thenReturn(nuevo);

        Administrador actualizado = administradorService.actualizarAdministrador(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Pedro Gómez", actualizado.getNombre());
    }

    @Test
    public void testPatchAdministrador() {
        Administrador existente = createAdministrador();
        Administrador patch = new Administrador();
        patch.setCargo("Subgerente");

        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(administradorRepository.save(ArgumentMatchers.any(Administrador.class))).thenReturn(existente);

        Administrador actualizado = administradorService.patchAdministrador(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Subgerente", actualizado.getCargo());
    }
}