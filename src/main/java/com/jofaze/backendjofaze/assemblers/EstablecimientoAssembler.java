package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.EstablecimientoController;
import com.jofaze.backendjofaze.model.Establecimiento;

@Component
public class EstablecimientoAssembler implements RepresentationModelAssembler<Establecimiento, EntityModel<Establecimiento>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Establecimiento> toModel(Establecimiento establecimiento) {
        return EntityModel.of(establecimiento,
            linkTo(methodOn(EstablecimientoController.class).buscarPorId(establecimiento.getId())).withSelfRel(),
            linkTo(methodOn(EstablecimientoController.class).listar()).withRel("establecimientos"),
            linkTo(methodOn(EstablecimientoController.class).actualizar(establecimiento.getId(), establecimiento)).withRel("actualizar"),
            linkTo(methodOn(EstablecimientoController.class).eliminar(establecimiento.getId())).withRel("eliminar"),
            linkTo(methodOn(EstablecimientoController.class).patchEstablecimiento(establecimiento.getId(), establecimiento)).withRel("actualizar-parcial")
        );
    }
}