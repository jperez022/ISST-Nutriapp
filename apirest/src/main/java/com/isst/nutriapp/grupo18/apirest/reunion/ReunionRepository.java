package com.isst.nutriapp.grupo18.apirest.reunion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository <Reunion, Integer> {

}
