package com.isst.nutriapp.grupo18.apirest.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaRepository extends JpaRepository <Dia, Integer> {

    boolean existsByNombreusuario(String nombre);

}
