package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.RegionController;
import com.jofaze.backendjofaze.model.Region;

@Component
public class RegionAssembler implements RepresentationModelAssembler<Region, EntityModel<Region>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Region> toModel(Region region) {
        return EntityModel.of(region,
            linkTo(methodOn(RegionController.class).buscarPorId(region.getId())).withSelfRel(),
            linkTo(methodOn(RegionController.class).listar()).withRel("regiones"),
            linkTo(methodOn(RegionController.class).actualizar(region.getId(), region)).withRel("actualizar"),
            linkTo(methodOn(RegionController.class).eliminar(region.getId())).withRel("eliminar"),
            linkTo(methodOn(RegionController.class).patchRegion(region.getId(), region)).withRel("actualizar-parcial")
        );
    }
}