package com.isst.nutriapp.grupo18.apirest.usuario;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public boolean usuarioExiste(String usuario) {
        return usuarioRepo.existsByNombre(usuario);
    }

    public void createUsuario(String nombre) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuarioRepo.save(usuario);
    }

    public void createObjetivos(String nombre, String peso, String ejercicio) {
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        String[] pesos = peso.split("-");
        String[] ejercicios = ejercicio.split("-");
        Integer peso_ini = Integer.parseInt(pesos[0]);
        Integer peso_act = Integer.parseInt(pesos[1]);
        Integer peso_obj = Integer.parseInt(pesos[2]);
        Integer ejercicio_act = Integer.parseInt(ejercicios[0]);
        Integer ejercicio_obj = Integer.parseInt(ejercicios[1]);
        usuario.setPeso_ini(peso_ini);
        usuario.setPeso_act(peso_act);
        usuario.setPeso_obj(peso_obj);
        usuario.setEjercicio_act(ejercicio_act);
        usuario.setEjercicio_obj(ejercicio_obj);
        usuarioRepo.save(usuario);
    }

    public ObjetivosJSON obtenerObjetivos(String nombre) {
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        ObjetivosJSON response = new ObjetivosJSON();
        response.setPeso_ini(usuario.getPeso_ini());
        response.setPeso_act(usuario.getPeso_act());
        response.setPeso_obj(usuario.getPeso_obj());
        response.setEjercicio_act(usuario.getEjercicio_act());
        response.setEjercicio_obj(usuario.getEjercicio_obj());
        return response;
    }

}