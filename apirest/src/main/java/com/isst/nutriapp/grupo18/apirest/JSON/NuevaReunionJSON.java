package com.isst.nutriapp.grupo18.apirest.JSON;

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
    private String hora;
    private String usuario;
}
