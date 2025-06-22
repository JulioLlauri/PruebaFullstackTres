package com.jofaze.backendjofaze.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.jofaze.backendjofaze.assemblers.HabitacionAssembler;
import com.jofaze.backendjofaze.model.Habitacion;
import com.jofaze.backendjofaze.service.HabitacionService;

@RestController
@RequestMapping("/api/v1/habitaciones")
@Tag(name = "Habitaciones", description = "Operaciones relacionadas con las habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las habitaciones", description = "Obtiene una lista de todas las habitaciones")
    public CollectionModel<EntityModel<Habitacion>> listar() {
        List<EntityModel<Habitacion>> habitaciones = habitacionService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(habitaciones,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HabitacionController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener habitación por ID", description = "Obtiene una habitación por su ID")
    public ResponseEntity<EntityModel<Habitacion>> buscarPorId(@PathVariable Long id) {
        return habitacionService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear habitación", description = "Crea una nueva habitación")
    public ResponseEntity<EntityModel<Habitacion>> guardar(@RequestBody Habitacion habitacion) {
        Habitacion nueva = habitacionService.save(habitacion);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HabitacionController.class).buscarPorId(nueva.getId())).toUri())
            .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar habitación", description = "Actualiza completamente una habitación existente")
    public ResponseEntity<EntityModel<Habitacion>> actualizar(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        Habitacion actualizada = habitacionService.actualizarHabitacion(id, habitacion);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente habitación", description = "Actualiza parcialmente una habitación existente")
    public ResponseEntity<EntityModel<Habitacion>> patchHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        Habitacion actualizada = habitacionService.patchHabitacion(id, habitacion);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar habitación", description = "Elimina una habitación por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (habitacionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        habitacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}