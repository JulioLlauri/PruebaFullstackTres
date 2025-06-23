package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import com.jofaze.backendjofaze.repository.TipoEstablecimientoRepository;
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
public class TipoEstablecimientoServiceTest {

    @Autowired
    private TipoEstablecimientoService tipoEstablecimientoService;

    @MockBean
    private TipoEstablecimientoRepository tipoEstablecimientoRepository;

    private TipoEstablecimiento createTipoEstablecimiento() {
        TipoEstablecimiento tipo = new TipoEstablecimiento();
        tipo.setId(1L);
        tipo.setDescripcion("Hotel");
        return tipo;
    }

    @Test
    public void testFindAll() {
        Mockito.when(tipoEstablecimientoRepository.findAll()).thenReturn(List.of(createTipoEstablecimiento()));
        List<TipoEstablecimiento> tipos = tipoEstablecimientoService.findAll();
        assertNotNull(tipos);
        assertEquals(1, tipos.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(tipoEstablecimientoRepository.findById(1L)).thenReturn(Optional.of(createTipoEstablecimiento()));
        Optional<TipoEstablecimiento> tipo = tipoEstablecimientoService.findById(1L);
        assertTrue(tipo.isPresent());
        assertEquals("Hotel", tipo.get().getDescripcion());
    }

    @Test
    public void testSave() {
        TipoEstablecimiento tipo = createTipoEstablecimiento();
        Mockito.when(tipoEstablecimientoRepository.save(tipo)).thenReturn(tipo);
        TipoEstablecimiento saved = tipoEstablecimientoService.save(tipo);
        assertNotNull(saved);
        assertEquals("Hotel", saved.getDescripcion());
    }

    @Test
    public void testActualizarTipoEstablecimiento() {
        TipoEstablecimiento existente = createTipoEstablecimiento();
        TipoEstablecimiento nuevo = createTipoEstablecimiento();
        nuevo.setDescripcion("Hostal");
        Mockito.when(tipoEstablecimientoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(tipoEstablecimientoRepository.save(ArgumentMatchers.any(TipoEstablecimiento.class))).thenReturn(nuevo);

        TipoEstablecimiento actualizado = tipoEstablecimientoService.actualizarTipoEstablecimiento(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Hostal", actualizado.getDescripcion());
    }

    @Test
    public void testPatchTipoEstablecimiento() {
        TipoEstablecimiento existente = createTipoEstablecimiento();
        TipoEstablecimiento patch = new TipoEstablecimiento();
        patch.setDescripcion("Residencial");

        Mockito.when(tipoEstablecimientoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(tipoEstablecimientoRepository.save(ArgumentMatchers.any(TipoEstablecimiento.class))).thenReturn(existente);

        TipoEstablecimiento actualizado = tipoEstablecimientoService.patchTipoEstablecimiento(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Residencial", actualizado.getDescripcion());
    }
}