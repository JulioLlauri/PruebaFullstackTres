package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.ClienteController;
import com.jofaze.backendjofaze.model.Cliente;

@Component
public class ClienteAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).listar()).withRel("clientes"),
            linkTo(methodOn(ClienteController.class).actualizar(cliente.getId(), cliente)).withRel("actualizar"),
            linkTo(methodOn(ClienteController.class).eliminar(cliente.getId())).withRel("eliminar"),
            linkTo(methodOn(ClienteController.class).patchCliente(cliente.getId(), cliente)).withRel("actualizar-parcial")
        );
    }
}