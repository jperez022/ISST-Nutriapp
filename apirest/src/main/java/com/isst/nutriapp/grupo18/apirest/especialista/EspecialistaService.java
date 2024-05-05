package com.isst.nutriapp.grupo18.apirest.especialista;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialistaService {

    private final EspecialistaRepository especialistaRepo;

    public List<Especialista> getEspecialistas() {
        return especialistaRepo.findAll();
    }

    public Especialista getEspecialistaUsuario(String usuario) {
        return especialistaRepo.findByUsuario(usuario);
    }

    public void setValoracion(String nombre, Integer valoracion) {
        Especialista especialista = especialistaRepo.findByNombre(nombre);
        especialista.setValoracion(valoracion);
        especialistaRepo.save(especialista);
    }

    public void createEspecialista(String nombre, String usuario, Integer movil, String info, Integer precio) {
        Especialista especialista = new Especialista();
        especialista.setNombre(nombre);
        especialista.setUsuario(usuario);
        especialista.setMovil(movil);
        especialista.setInfo(info);
        especialista.setPrecio(precio);
        especialistaRepo.save(especialista);
    }

}
