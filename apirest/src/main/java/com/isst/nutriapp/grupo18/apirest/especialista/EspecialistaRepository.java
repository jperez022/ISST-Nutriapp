package com.isst.nutriapp.grupo18.apirest.especialista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EspecialistaRepository extends JpaRepository <Especialista, Integer> {

    Especialista findByNombre(String nombre);

    Especialista findByUsuario(String usuario);

}
