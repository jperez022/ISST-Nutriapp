package com.isst.nutriapp.grupo18.apirest.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isst.nutriapp.grupo18.apirest.JSON.FotoNumJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.ObjetivosJSON;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public boolean usuarioExiste(String usuario) {
        return usuarioRepo.existsByNombre(usuario);
    }

    public void createUsuario(String nombre) {
        if (!usuarioExiste(nombre)) {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setPremium(false);
            usuario.setSpecialist(false);
            usuarioRepo.save(usuario);
        }
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

    public void hacerPremium(String nombre) {
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        usuario.setPremium(true);
        usuarioRepo.save(usuario);
    }

    public boolean esPremium(String nombre) {
        if (usuarioRepo.existsByNombre(nombre)) {
            Usuario usuario = usuarioRepo.findByNombre(nombre);
            return usuario.getPremium();
        }
        return false;
    }

    public void hacerEspecialista(String nombre) {
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        usuario.setSpecialist(true);
        usuarioRepo.save(usuario);
    }

    public boolean esEspecialista(String nombre) {
        if (usuarioRepo.existsByNombre(nombre)) {
            Usuario usuario = usuarioRepo.findByNombre(nombre);
            return usuario.getSpecialist();
        }
        return false;
    }

    public FotoNumJSON numFoto(String nombre) {
        FotoNumJSON numFoto = new FotoNumJSON();
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        numFoto.setResp(usuario.getFoto());
        return numFoto;
    }

    public void setFoto(String nombre) {
        Optional<Usuario> aux_usuario = usuarioRepo.findUsuarioWithMaxFoto();
        Integer num = aux_usuario.get().getFoto();
        if (num == null) {
            num = 0;
        }
        Usuario usuario = usuarioRepo.findByNombre(nombre);
        usuario.setFoto(num);
        usuarioRepo.save(usuario);
    }

}
