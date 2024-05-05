package com.isst.nutriapp.grupo18.apirest.JSON;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevaReunionJSON {
    private String titulo;
    private String link;
    private String fecha;
    private Time hora;
    private String usuario;
}
