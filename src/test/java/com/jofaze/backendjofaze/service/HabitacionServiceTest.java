package com.jofaze.backendjofaze.service;

import com.jofaze.backendjofaze.model.Habitacion;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.model.Cliente;
import com.jofaze.backendjofaze.repository.HabitacionRepository;
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
public class HabitacionServiceTest {

    @Autowired
    private HabitacionService habitacionService;

    @MockBean
    private HabitacionRepository habitacionRepository;

    private Habitacion createHabitacion() {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(1L);
        habitacion.setNumero("101");
        habitacion.setTipo("Suite");
        habitacion.setCapacidad(2);
        habitacion.setEstablecimiento(new Establecimiento());
        habitacion.setCliente(new Cliente());
        return habitacion;
    }

    @Test
    public void testFindAll() {
        Mockito.when(habitacionRepository.findAll()).thenReturn(List.of(createHabitacion()));
        List<Habitacion> habitaciones = habitacionService.findAll();
        assertNotNull(habitaciones);
        assertEquals(1, habitaciones.size());
    }

    @Test
    public void testFindById() {
        Mockito.when(habitacionRepository.findById(1L)).thenReturn(Optional.of(createHabitacion()));
        Optional<Habitacion> habitacion = habitacionService.findById(1L);
        assertTrue(habitacion.isPresent());
        assertEquals("101", habitacion.get().getNumero());
    }

    @Test
    public void testSave() {
        Habitacion habitacion = createHabitacion();
        Mockito.when(habitacionRepository.save(habitacion)).thenReturn(habitacion);
        Habitacion saved = habitacionService.save(habitacion);
        assertNotNull(saved);
        assertEquals("101", saved.getNumero());
    }

    @Test
    public void testActualizarHabitacion() {
        Habitacion existente = createHabitacion();
        Habitacion nuevo = createHabitacion();
        nuevo.setNumero("202");
        Mockito.when(habitacionRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(habitacionRepository.save(ArgumentMatchers.any(Habitacion.class))).thenReturn(nuevo);

        Habitacion actualizado = habitacionService.actualizarHabitacion(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("202", actualizado.getNumero());
    }

    @Test
    public void testPatchHabitacion() {
        Habitacion existente = createHabitacion();
        Habitacion patch = new Habitacion();
        patch.setTipo("Doble");

        Mockito.when(habitacionRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(habitacionRepository.save(ArgumentMatchers.any(Habitacion.class))).thenReturn(existente);

        Habitacion actualizado = habitacionService.patchHabitacion(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Doble", actualizado.getTipo());
    }
}