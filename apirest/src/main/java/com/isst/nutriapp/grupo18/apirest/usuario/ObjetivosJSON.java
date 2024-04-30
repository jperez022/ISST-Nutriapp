package com.isst.nutriapp.grupo18.apirest.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjetivosJSON {
    private Integer peso_ini;
    private Integer peso_act;
    private Integer peso_obj;
    private Integer ejercicio_act;
    private Integer ejercicio_obj;
}
