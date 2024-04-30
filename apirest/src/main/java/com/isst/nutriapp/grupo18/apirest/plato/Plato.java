package com.isst.nutriapp.grupo18.apirest.plato;

import java.util.List;

import com.isst.nutriapp.grupo18.apirest.dia.Dia;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plato {
    @Id
    @GeneratedValue
    private Integer Id;
    @Basic
    private String nombre;
    private String preparacion;
    private String ingredientes;
    private String cantidades;
    private String calorias;
    private Integer caloriasTot;
    private boolean sugerido;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "plato_dia",
        joinColumns = @JoinColumn(name = "plato_id"),
        inverseJoinColumns = @JoinColumn(name = "dia_id")
    )
    private List<Dia> dias;
}
