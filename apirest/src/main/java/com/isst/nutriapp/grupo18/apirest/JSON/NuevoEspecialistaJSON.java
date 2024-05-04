package com.isst.nutriapp.grupo18.apirest.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoEspecialistaJSON {
    private String nombre;
    private Integer movil;
    private String info;
    private Integer precio;
}
