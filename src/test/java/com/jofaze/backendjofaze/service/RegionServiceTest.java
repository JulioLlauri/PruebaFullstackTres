package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Region;
import com.jofaze.backendjofaze.repository.RegionRepository;
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
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @MockBean
    private RegionRepository regionRepository;

    private Region createRegion() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Región Metropolitana");
        return region;
    }

    @Test
    public void testFindAll() {
        Mockito.when(regionRepository.findAll()).thenReturn(List.of(createRegion()));
        List<Region> regiones = regionService.findAll();
        assertNotNull(regiones);
        assertEquals(1, regiones.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(createRegion()));
        Optional<Region> region = regionService.findById(1L);
        assertTrue(region.isPresent());
        assertEquals("Región Metropolitana", region.get().getNombre());
    }

    @Test
    public void testSave() {
        Region region = createRegion();
        Mockito.when(regionRepository.save(region)).thenReturn(region);
        Region saved = regionService.save(region);
        assertNotNull(saved);
        assertEquals("Región Metropolitana", saved.getNombre());
    }

    @Test
    public void testActualizarRegion() {
        Region existente = createRegion();
        Region nuevo = createRegion();
        nuevo.setNombre("Región de Valparaíso");
        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(regionRepository.save(ArgumentMatchers.any(Region.class))).thenReturn(nuevo);

        Region actualizado = regionService.actualizarRegion(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Región de Valparaíso", actualizado.getNombre());
    }

    @Test
    public void testPatchRegion() {
        Region existente = createRegion();
        Region patch = new Region();
        patch.setNombre("Región del Biobío");

        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(regionRepository.save(ArgumentMatchers.any(Region.class))).thenReturn(existente);

        Region actualizado = regionService.patchRegion(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Región del Biobío", actualizado.getNombre());
    }
}