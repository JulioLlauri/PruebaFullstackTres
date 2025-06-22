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

import com.jofaze.backendjofaze.assemblers.ContactoAssembler;
import com.jofaze.backendjofaze.model.Contacto;
import com.jofaze.backendjofaze.service.ContactoService;

@RestController
@RequestMapping("/api/v1/contactos")
@Tag(name = "Contactos", description = "Operaciones relacionadas con los contactos")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private ContactoAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los contactos", description = "Obtiene una lista de todos los contactos")
    public CollectionModel<EntityModel<Contacto>> listar() {
        List<EntityModel<Contacto>> contactos = contactoService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(contactos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContactoController.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener contacto por ID", description = "Obtiene un contacto por su ID")
    public ResponseEntity<EntityModel<Contacto>> buscarPorId(@PathVariable Long id) {
        return contactoService.findById(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear contacto", description = "Crea un nuevo contacto")
    public ResponseEntity<EntityModel<Contacto>> guardar(@RequestBody Contacto contacto) {
        Contacto nuevoContacto = contactoService.save(contacto);
        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContactoController.class).buscarPorId(nuevoContacto.getId())).toUri())
            .body(assembler.toModel(nuevoContacto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar contacto", description = "Actualiza completamente un contacto existente")
    public ResponseEntity<EntityModel<Contacto>> actualizar(@PathVariable Long id, @RequestBody Contacto contacto) {
        Contacto actualizado = contactoService.actualizarContacto(id, contacto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente contacto", description = "Actualiza parcialmente un contacto existente")
    public ResponseEntity<EntityModel<Contacto>> patchContacto(@PathVariable Long id, @RequestBody Contacto contacto) {
        Contacto actualizado = contactoService.patchContacto(id, contacto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar contacto", description = "Elimina un contacto por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (contactoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        contactoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}