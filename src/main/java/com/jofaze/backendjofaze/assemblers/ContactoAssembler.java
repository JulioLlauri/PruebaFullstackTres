package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.ContactoController;
import com.jofaze.backendjofaze.model.Contacto;

@Component
public class ContactoAssembler implements RepresentationModelAssembler<Contacto, EntityModel<Contacto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Contacto> toModel(Contacto contacto) {
        return EntityModel.of(contacto,
            linkTo(methodOn(ContactoController.class).buscarPorId(contacto.getId())).withSelfRel(),
            linkTo(methodOn(ContactoController.class).listar()).withRel("contactos"),
            linkTo(methodOn(ContactoController.class).actualizar(contacto.getId(), contacto)).withRel("actualizar"),
            linkTo(methodOn(ContactoController.class).eliminar(contacto.getId())).withRel("eliminar"),
            linkTo(methodOn(ContactoController.class).patchContacto(contacto.getId(), contacto)).withRel("actualizar-parcial")
        );
    }
}