package com.isst.nutriapp.grupo18.apirest.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {

    Usuario findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
