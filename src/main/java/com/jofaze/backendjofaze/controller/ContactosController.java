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

import com.jofaze.backendjofaze.assemblers.ContactosAssembler;
import com.jofaze.backendjofaze.model.Contactos;
import com.jofaze.backendjofaze.service.ContactosService;

@RestController
@RequestMapping("/api/v1/contactos-establecimientos")
@Tag(name = "Contactos-Establecimientos", description = "Relación entre contactos y establecimientos")
public class ContactosController {

    @Autowired
    private ContactosService contactosService;

    @Autowired
    private ContactosAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las relaciones", description = "Obtiene todas las relaciones contacto-establecimiento")
    public CollectionModel<EntityModel<Contactos>> getAll() {
        List<EntityModel<Contactos>> lista = contactosService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(lista,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContactosController.class).getAll()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener relación por ID", description = "Obtiene  por su ID")
    public ResponseEntity<EntityModel<Contactos>> getById(@PathVariable Long id) {
        return contactosService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear ", description = "Crea  contacto-establecimiento")
    public ResponseEntity<EntityModel<Contactos>> create(@RequestBody Contactos contactos) {
        Contactos nuevo = contactosService.save(contactos);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContactosController.class).getById(nuevo.getId())).toUri())
            .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar ", description = "Actualiza completamente ")
    public ResponseEntity<EntityModel<Contactos>> update(@PathVariable Long id, @RequestBody Contactos contactos) {
        Contactos actualizado = contactosService.actualizarContactos(id, contactos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente relación", description = "Actualiza parcialmente ")
    public ResponseEntity<EntityModel<Contactos>> patchContactos(@PathVariable Long id, @RequestBody Contactos contactos) {
        Contactos actualizado = contactosService.patchContactos(id, contactos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar relación", description = "Elimina  por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (contactosService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        contactosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}