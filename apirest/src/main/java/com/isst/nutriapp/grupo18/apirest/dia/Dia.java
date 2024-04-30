package com.isst.nutriapp.grupo18.apirest.dia;

import java.util.List;

import com.isst.nutriapp.grupo18.apirest.plato.Plato;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dia {
    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    private String nombreusuario;
    private Integer dia;
    private Integer mes;
    @ManyToMany(mappedBy = "dias", cascade = CascadeType.ALL)
    private List<Plato> platos;
}
