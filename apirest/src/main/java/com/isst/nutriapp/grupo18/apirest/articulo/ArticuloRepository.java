package com.isst.nutriapp.grupo18.apirest.articulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository <Articulo, Integer> {

}
