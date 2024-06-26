package com.isst.nutriapp.grupo18.apirest.especialista;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario"})})
public class Especialista {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String nombre;
    private String usuario;
    private Integer movil;
    private String info;
    private Integer valoracion;
    private Integer precio;
}
