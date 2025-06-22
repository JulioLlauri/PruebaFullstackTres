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

import com.jofaze.backendjofaze.assemblers.TipoEstablecimientoAssembler;
import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import com.jofaze.backendjofaze.service.TipoEstablecimientoService;

@RestController
@RequestMapping("/api/v1/tipos-establecimiento")
@Tag(name = "Tipos de Establecimiento", description = "Operaciones relacionadas con los tipos de establecimiento")
public class TipoEstablecimientoController {

    @Autowired
    private TipoEstablecimientoService tipoEstablecimientoService;

    @Autowired
    private TipoEstablecimientoAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los tipos de establecimiento", description = "Obtiene una lista de todos los tipos de establecimiento")
    public CollectionModel<EntityModel<TipoEstablecimiento>> listar() {
        List<EntityModel<TipoEstablecimiento>> tipos = tipoEstablecimientoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(tipos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TipoEstablecimientoController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener tipo de establecimiento por ID", description = "Obtiene un tipo de establecimiento por su ID")
    public ResponseEntity<EntityModel<TipoEstablecimiento>> buscarPorId(@PathVariable Long id) {
        return tipoEstablecimientoService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear tipo de establecimiento", description = "Crea un nuevo tipo de establecimiento")
    public ResponseEntity<EntityModel<TipoEstablecimiento>> guardar(@RequestBody TipoEstablecimiento tipoEstablecimiento) {
        TipoEstablecimiento nuevo = tipoEstablecimientoService.save(tipoEstablecimiento);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TipoEstablecimientoController.class).buscarPorId(nuevo.getId())).toUri())
            .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar tipo de establecimiento", description = "Actualiza completamente un tipo de establecimiento existente")
    public ResponseEntity<EntityModel<TipoEstablecimiento>> actualizar(@PathVariable Long id, @RequestBody TipoEstablecimiento tipoEstablecimiento) {
        TipoEstablecimiento actualizado = tipoEstablecimientoService.actualizarTipoEstablecimiento(id, tipoEstablecimiento);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente tipo de establecimiento", description = "Actualiza parcialmente un tipo de establecimiento existente")
    public ResponseEntity<EntityModel<TipoEstablecimiento>> patchTipoEstablecimiento(@PathVariable Long id, @RequestBody TipoEstablecimiento tipoEstablecimiento) {
        TipoEstablecimiento actualizado = tipoEstablecimientoService.patchTipoEstablecimiento(id, tipoEstablecimiento);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar tipo de establecimiento", description = "Elimina un tipo de establecimiento por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (tipoEstablecimientoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoEstablecimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}