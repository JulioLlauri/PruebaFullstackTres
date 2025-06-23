package com.jofaze.backendjofaze.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jofaze.backendjofaze.controller.ClienteController;
import com.jofaze.backendjofaze.dto.ClienteDTO;
import com.jofaze.backendjofaze.model.Cliente;

@Component
public class ClienteAssembler implements RepresentationModelAssembler<Cliente, EntityModel<ClienteDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<ClienteDTO> toModel(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        
        return EntityModel.of(dto,
            linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).listar()).withRel("clientes"),
            linkTo(methodOn(ClienteController.class).actualizar(cliente.getId(), cliente)).withRel("actualizar"),
            linkTo(methodOn(ClienteController.class).eliminar(cliente.getId())).withRel("eliminar"),
            linkTo(methodOn(ClienteController.class).patchCliente(cliente.getId(), cliente)).withRel("actualizar-parcial")
        );
    }
}