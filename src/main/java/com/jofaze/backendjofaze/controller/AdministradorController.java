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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jofaze.backendjofaze.assemblers.AdministradorAssembler;
import com.jofaze.backendjofaze.model.Administrador;
import com.jofaze.backendjofaze.service.AdministradorService;

@RestController
@RequestMapping("/api/v1/administradores")
@Tag(name = "Administradores", description = "Operaciones relacionadas con los administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AdministradorAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los administradores", description = "Obtiene una lista de todos los administradores")
    public CollectionModel<EntityModel<Administrador>> listar() {
        List<EntityModel<Administrador>> administradores = administradorService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(administradores,
            linkTo(methodOn(AdministradorController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Administrador>> buscarPorId(@PathVariable Long id) {
        Administrador administrador = administradorService.findById(id).orElse(null);
        if (administrador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(administrador));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Administrador>> guardar(@RequestBody Administrador administrador) {
        Administrador nuevoAdministrador = administradorService.save(administrador);
        return ResponseEntity
            .created(linkTo(methodOn(AdministradorController.class).buscarPorId(nuevoAdministrador.getId())).toUri())
            .body(assembler.toModel(nuevoAdministrador));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Administrador>> actualizar(@PathVariable Long id, @RequestBody Administrador administrador) {
        administrador.setId(id);
        Administrador actualizado = administradorService.save(administrador);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Administrador>> patchAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        Administrador actualizado = administradorService.patchAdministrador(id, administrador);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Administrador administrador = administradorService.findById(id).orElse(null);
        if (administrador == null) {
            return ResponseEntity.notFound().build();
        }
        administradorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}