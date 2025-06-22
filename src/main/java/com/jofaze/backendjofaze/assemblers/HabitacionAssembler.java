package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.HabitacionController;
import com.jofaze.backendjofaze.model.Habitacion;

@Component
public class HabitacionAssembler implements RepresentationModelAssembler<Habitacion, EntityModel<Habitacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Habitacion> toModel(Habitacion habitacion) {
        return EntityModel.of(habitacion,
            linkTo(methodOn(HabitacionController.class).buscarPorId(habitacion.getId())).withSelfRel(),
            linkTo(methodOn(HabitacionController.class).listar()).withRel("habitaciones"),
            linkTo(methodOn(HabitacionController.class).actualizar(habitacion.getId(), habitacion)).withRel("actualizar"),
            linkTo(methodOn(HabitacionController.class).eliminar(habitacion.getId())).withRel("eliminar"),
            linkTo(methodOn(HabitacionController.class).patchHabitacion(habitacion.getId(), habitacion)).withRel("actualizar-parcial")
        );
    }
}