package com.isst.nutriapp.grupo18.apirest.usuario;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Usuario {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String nombre;
    private Integer foto;
    private Integer peso_ini;
    private Integer peso_act;
    private Integer peso_obj;
    private Integer ejercicio_act;
    private Integer ejercicio_obj;
    private Boolean specialist;
    private Boolean premium;
}
