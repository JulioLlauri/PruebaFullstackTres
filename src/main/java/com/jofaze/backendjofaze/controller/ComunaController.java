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

import com.jofaze.backendjofaze.assemblers.ComunaAssembler;
import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.service.ComunaService;

@RestController
@RequestMapping("/api/v1/comunas")
@Tag(name = "Comunas", description = "Operaciones relacionadas con las comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las comunas", description = "Obtiene una lista de todas las comunas")
    public CollectionModel<EntityModel<Comuna>> listar() {
        List<EntityModel<Comuna>> comunas = comunaService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(comunas,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunaController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener comuna por ID", description = "Obtiene una comuna por su ID")
    public ResponseEntity<EntityModel<Comuna>> buscarPorId(@PathVariable Long id) {
        return comunaService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear comuna", description = "Crea una nueva comuna")
    public ResponseEntity<EntityModel<Comuna>> guardar(@RequestBody Comuna comuna) {
        Comuna nuevaComuna = comunaService.save(comuna);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunaController.class).buscarPorId(nuevaComuna.getId())).toUri())
            .body(assembler.toModel(nuevaComuna));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar comuna", description = "Actualiza completamente una comuna existente")
    public ResponseEntity<EntityModel<Comuna>> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizada = comunaService.actualizarComuna(id, comuna);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente comuna", description = "Actualiza parcialmente una comuna existente")
    public ResponseEntity<EntityModel<Comuna>> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizada = comunaService.patchComuna(id, comuna);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (comunaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}