package com.isst.nutriapp.grupo18.apirest.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepository extends JpaRepository <Plato, Integer> {

    List<Plato> findByDias_Id(Integer id);

    List<Plato> findByCaloriasTotLessThanEqualAndCaloriasTotGreaterThanEqualAndSugerido(Integer toCalorias_tot, Integer fromCalorias_tot, boolean sugerido);

}
