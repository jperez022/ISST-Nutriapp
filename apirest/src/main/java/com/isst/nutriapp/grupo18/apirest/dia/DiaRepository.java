package com.isst.nutriapp.grupo18.apirest.dia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface DiaRepository extends JpaRepository <Dia, Integer> {

    boolean existsByNombreusuario(String nombre);

    List<Dia> findByNombreusuario(String nombreusuario);

}
