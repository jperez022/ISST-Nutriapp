package com.isst.nutriapp.grupo18.apirest.especialista;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Especialista {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String nombre;
    private Integer movil;
    private String info;
    private Integer valoracion;
    private Integer precio;
}