package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import com.jofaze.backendjofaze.repository.EstablecimientoRepository;
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
public class EstablecimientoServiceTest {

    @Autowired
    private EstablecimientoService establecimientoService;

    @MockBean
    private EstablecimientoRepository establecimientoRepository;

    private Establecimiento createEstablecimiento() {
        Establecimiento est = new Establecimiento();
        est.setId(1L);
        est.setNombre("Hotel Central");
        est.setDireccion("Calle Falsa 123");
        est.setTelefono("987654321");
        est.setEmail("hotel@central.com");
        est.setComuna(new Comuna());
        est.setTipoEstablecimiento(new TipoEstablecimiento());
        return est;
    }

    @Test
    public void testFindAll() {
        Mockito.when(establecimientoRepository.findAll()).thenReturn(List.of(createEstablecimiento()));
        List<Establecimiento> lista = establecimientoService.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(establecimientoRepository.findById(1L)).thenReturn(Optional.of(createEstablecimiento()));
        Optional<Establecimiento> est = establecimientoService.findById(1L);
        assertTrue(est.isPresent());
        assertEquals("Hotel Central", est.get().getNombre());
    }

    @Test
    public void testSave() {
        Establecimiento est = createEstablecimiento();
        Mockito.when(establecimientoRepository.save(est)).thenReturn(est);
        Establecimiento saved = establecimientoService.save(est);
        assertNotNull(saved);
        assertEquals("Hotel Central", saved.getNombre());
    }

    @Test
    public void testActualizarEstablecimiento() {
        Establecimiento existente = createEstablecimiento();
        Establecimiento nuevo = createEstablecimiento();
        nuevo.setNombre("Hotel Plaza");
        Mockito.when(establecimientoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(establecimientoRepository.save(ArgumentMatchers.any(Establecimiento.class))).thenReturn(nuevo);

        Establecimiento actualizado = establecimientoService.actualizarEstablecimiento(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Hotel Plaza", actualizado.getNombre());
    }

    @Test
    public void testPatchEstablecimiento() {
        Establecimiento existente = createEstablecimiento();
        Establecimiento patch = new Establecimiento();
        patch.setTelefono("123123123");

        Mockito.when(establecimientoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(establecimientoRepository.save(ArgumentMatchers.any(Establecimiento.class))).thenReturn(existente);

        Establecimiento actualizado = establecimientoService.patchEstablecimiento(1L, patch);
        assertNotNull(actualizado);
        assertEquals("123123123", actualizado.getTelefono());
    }
}