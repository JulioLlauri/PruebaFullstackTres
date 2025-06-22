package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.ComunaController;
import com.jofaze.backendjofaze.model.Comuna;

@Component
public class ComunaAssembler implements RepresentationModelAssembler<Comuna, EntityModel<Comuna>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Comuna> toModel(Comuna comuna) {
        return EntityModel.of(comuna,
            linkTo(methodOn(ComunaController.class).buscarPorId(comuna.getId())).withSelfRel(),
            linkTo(methodOn(ComunaController.class).listar()).withRel("comunas"),
            linkTo(methodOn(ComunaController.class).actualizar(comuna.getId(), comuna)).withRel("actualizar"),
            linkTo(methodOn(ComunaController.class).eliminar(comuna.getId())).withRel("eliminar"),
            linkTo(methodOn(ComunaController.class).patchComuna(comuna.getId(), comuna)).withRel("actualizar-parcial")
        );
    }
}