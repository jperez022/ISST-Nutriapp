package com.isst.nutriapp.grupo18.apirest.articulo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Articulo {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String titulo;
    @Lob
    @Column(length = 16777216)
    private String descripcion;
    private String url;
}
