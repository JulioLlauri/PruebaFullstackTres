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

import com.jofaze.backendjofaze.assemblers.RegionAssembler;
import com.jofaze.backendjofaze.model.Region;
import com.jofaze.backendjofaze.service.RegionService;

@RestController
@RequestMapping("/api/v1/regiones")
@Tag(name = "Regiones", description = "Operaciones relacionadas con las regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las regiones", description = "Obtiene una lista de todas las regiones")
    public CollectionModel<EntityModel<Region>> listar() {
        List<EntityModel<Region>> regiones = regionService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(regiones,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegionController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener región por ID", description = "Obtiene una región por su ID")
    public ResponseEntity<EntityModel<Region>> buscarPorId(@PathVariable Long id) {
        return regionService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear región", description = "Crea una nueva región")
    public ResponseEntity<EntityModel<Region>> guardar(@RequestBody Region region) {
        Region nueva = regionService.save(region);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegionController.class).buscarPorId(nueva.getId())).toUri())
            .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar región", description = "Actualiza completamente una región existente")
    public ResponseEntity<EntityModel<Region>> actualizar(@PathVariable Long id, @RequestBody Region region) {
        Region actualizada = regionService.actualizarRegion(id, region);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente región", description = "Actualiza parcialmente una región existente")
    public ResponseEntity<EntityModel<Region>> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region actualizada = regionService.patchRegion(id, region);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar región", description = "Elimina una región por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (regionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}