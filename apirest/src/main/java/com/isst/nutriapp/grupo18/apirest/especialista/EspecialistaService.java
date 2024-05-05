package com.isst.nutriapp.grupo18.apirest.especialista;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialistaService {

    private final EspecialistaRepository especialistaRepo;

    public List<Especialista> getEspecialistas() {
        List<Especialista> print = especialistaRepo.findAll();
        System.err.println(print);
        return especialistaRepo.findAll();
    }

    public void createEspecialista(String nombre, Integer movil, String info, Integer precio) {
        Especialista especialista = new Especialista();
        especialista.setNombre(nombre);
        especialista.setMovil(movil);
        especialista.setInfo(info);
        especialista.setPrecio(precio);
        especialistaRepo.save(especialista);
    }

}
