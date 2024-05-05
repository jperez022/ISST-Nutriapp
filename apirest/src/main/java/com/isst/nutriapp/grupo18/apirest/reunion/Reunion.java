package com.isst.nutriapp.grupo18.apirest.reunion;

import java.sql.Time;

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
public class Reunion {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String titulo;
    private String link;
    private Integer dia;
    private Integer mes;
    private Time hora;
    private String usuario;
}
