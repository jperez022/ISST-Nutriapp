package com.isst.nutriapp.grupo18.apirest.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoPlatoJSON {
    private String usuario;
    private String nombre;
    private String preparacion;
    private String ingredientes;
    private String descripcion;
    private String calorias;
    private Integer calorias_total;
    private String dia_mes;
}
