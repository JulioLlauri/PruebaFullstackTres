package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.TipoEstablecimientoController;
import com.jofaze.backendjofaze.model.TipoEstablecimiento;

@Component
public class TipoEstablecimientoAssembler implements RepresentationModelAssembler<TipoEstablecimiento, EntityModel<TipoEstablecimiento>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoEstablecimiento> toModel(TipoEstablecimiento tipoEstablecimiento) {
        return EntityModel.of(tipoEstablecimiento,
            linkTo(methodOn(TipoEstablecimientoController.class).buscarPorId(tipoEstablecimiento.getId())).withSelfRel(),
            linkTo(methodOn(TipoEstablecimientoController.class).listar()).withRel("tipos-establecimiento"),
            linkTo(methodOn(TipoEstablecimientoController.class).actualizar(tipoEstablecimiento.getId(), tipoEstablecimiento)).withRel("actualizar"),
            linkTo(methodOn(TipoEstablecimientoController.class).eliminar(tipoEstablecimiento.getId())).withRel("eliminar"),
            linkTo(methodOn(TipoEstablecimientoController.class).patchTipoEstablecimiento(tipoEstablecimiento.getId(), tipoEstablecimiento)).withRel("actualizar-parcial")
        );
    }
}