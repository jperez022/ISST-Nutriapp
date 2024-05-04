package com.isst.nutriapp.grupo18.apirest.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoArticuloJSON {
    private String titulo;
    private String descripcion;
    private String url;
}
