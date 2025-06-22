package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.ContactosController;
import com.jofaze.backendjofaze.model.Contactos;

@Component
public class ContactosAssembler implements RepresentationModelAssembler<Contactos, EntityModel<Contactos>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Contactos> toModel(Contactos contactos) {
        return EntityModel.of(contactos,
            linkTo(methodOn(ContactosController.class).getById(contactos.getId())).withSelfRel(),
            linkTo(methodOn(ContactosController.class).getAll()).withRel("contactos-establecimientos"),
            linkTo(methodOn(ContactosController.class).update(contactos.getId(), contactos)).withRel("actualizar"),
            linkTo(methodOn(ContactosController.class).delete(contactos.getId())).withRel("eliminar"),
            linkTo(methodOn(ContactosController.class).patchContactos(contactos.getId(), contactos)).withRel("actualizar-parcial")
        );
    }
}