package com.jofaze.backendjofaze.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

}
