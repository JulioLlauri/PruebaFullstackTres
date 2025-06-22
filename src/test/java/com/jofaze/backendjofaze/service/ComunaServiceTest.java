package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.model.Region;
import com.jofaze.backendjofaze.repository.ComunaRepository;
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
public class ComunaServiceTest {

    @Autowired
    private ComunaService comunaService;

    @MockBean
    private ComunaRepository comunaRepository;

    private Comuna createComuna() {
        Comuna comuna = new Comuna();
        comuna.setId(1L);
        comuna.setNombre("Santiago Centro");
        comuna.setRegion(new Region(1L, "Región Metropolitana"));
        return comuna;
    }

    @Test
    public void testFindAll() {
        Mockito.when(comunaRepository.findAll()).thenReturn(List.of(createComuna()));
        List<Comuna> comunas = comunaService.findAll();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(comunaRepository.findById(1L)).thenReturn(Optional.of(createComuna()));
        Optional<Comuna> comuna = comunaService.findById(1L);
        assertTrue(comuna.isPresent());
        assertEquals("Santiago Centro", comuna.get().getNombre());
    }

    @Test
    public void testSave() {
        Comuna comuna = createComuna();
        Mockito.when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna saved = comunaService.save(comuna);
        assertNotNull(saved);
        assertEquals("Santiago Centro", saved.getNombre());
    }

    @Test
    public void testActualizarComuna() {
        Comuna existente = createComuna();
        Comuna nuevo = createComuna();
        nuevo.setNombre("Providencia");
        Mockito.when(comunaRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(comunaRepository.save(ArgumentMatchers.any(Comuna.class))).thenReturn(nuevo);

        Comuna actualizado = comunaService.actualizarComuna(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Providencia", actualizado.getNombre());
    }

    @Test
    public void testPatchComuna() {
        Comuna existente = createComuna();
        Comuna patch = new Comuna();
        patch.setNombre("Ñuñoa");

        Mockito.when(comunaRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(comunaRepository.save(ArgumentMatchers.any(Comuna.class))).thenReturn(existente);

        Comuna actualizado = comunaService.patchComuna(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Ñuñoa", actualizado.getNombre());
    }
}