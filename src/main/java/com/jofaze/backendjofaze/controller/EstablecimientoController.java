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

import com.jofaze.backendjofaze.assemblers.EstablecimientoAssembler;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.service.EstablecimientoService;

@RestController
@RequestMapping("/api/v1/establecimientos")
@Tag(name = "Establecimientos", description = "Operaciones relacionadas con los establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @Autowired
    private EstablecimientoAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los establecimientos", description = "Obtiene una lista de todos los establecimientos")
    public CollectionModel<EntityModel<Establecimiento>> listar() {
        List<EntityModel<Establecimiento>> establecimientos = establecimientoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(establecimientos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstablecimientoController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener establecimiento por ID", description = "Obtiene un establecimiento por su ID")
    public ResponseEntity<EntityModel<Establecimiento>> buscarPorId(@PathVariable Long id) {
        return establecimientoService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear establecimiento", description = "Crea un nuevo establecimiento")
    public ResponseEntity<EntityModel<Establecimiento>> guardar(@RequestBody Establecimiento establecimiento) {
        Establecimiento nuevo = establecimientoService.save(establecimiento);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstablecimientoController.class).buscarPorId(nuevo.getId())).toUri())
            .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar establecimiento", description = "Actualiza completamente un establecimiento existente")
    public ResponseEntity<EntityModel<Establecimiento>> actualizar(@PathVariable Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento actualizado = establecimientoService.actualizarEstablecimiento(id, establecimiento);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente establecimiento", description = "Actualiza parcialmente un establecimiento existente")
    public ResponseEntity<EntityModel<Establecimiento>> patchEstablecimiento(@PathVariable Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento actualizado = establecimientoService.patchEstablecimiento(id, establecimiento);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar establecimiento", description = "Elimina un establecimiento por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (establecimientoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        establecimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}