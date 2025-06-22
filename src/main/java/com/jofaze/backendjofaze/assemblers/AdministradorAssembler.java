package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.AdministradorController;
import com.jofaze.backendjofaze.model.Administrador;

@Component
public class AdministradorAssembler implements RepresentationModelAssembler<Administrador, EntityModel<Administrador>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Administrador> toModel(Administrador administrador) {
        return EntityModel.of(administrador,

            linkTo(methodOn(AdministradorController.class).buscarPorId(administrador.getId())).withSelfRel(),
            linkTo(methodOn(AdministradorController.class).listar()).withRel("administradores"),
            linkTo(methodOn(AdministradorController.class).actualizar(administrador.getId(), administrador)).withRel("actualizar"),
            linkTo(methodOn(AdministradorController.class).eliminar(administrador.getId())).withRel("eliminar"),
            linkTo(methodOn(AdministradorController.class).patchAdministrador(administrador.getId(), administrador)).withRel("actualizar-parcial")
            
        );
    }
}