package com.isst.nutriapp.grupo18.apirest.dia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.isst.nutriapp.grupo18.apirest.JSON.PlatosJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.RespJSON;
import com.isst.nutriapp.grupo18.apirest.plato.Plato;
import com.isst.nutriapp.grupo18.apirest.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaService {

    private final DiaRepository diaRepo;
    private final UsuarioRepository usuarioRepo;

    public boolean usuarioExiste(String usuario) {
        return usuarioRepo.existsByNombre(usuario);
    }

    public void createCalendario(String nombre) {
        if (!diaRepo.existsByNombreusuario(nombre)) {
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(1);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 30; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(2);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(3);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(4);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(5);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(6);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(7);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(8);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(9);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(10);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(11);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(12);
                dia.setReunion(false);
                diaRepo.save(dia);
            }
        }
    }

    public ArrayList<PlatosJSON> getPlatosDia(String nombre, Integer mes, Integer dia) {
        Dia newdia = new Dia();
        newdia.setNombreusuario(nombre);
        newdia.setDia(dia);
        newdia.setMes(mes);
        Optional<Dia> diaid = diaRepo.findOne(Example.of(newdia));
        List<Plato> platos = diaid.get().getPlatos();
        ArrayList<PlatosJSON> list = new ArrayList<PlatosJSON>();
        if (!platos.isEmpty()) {
            for (Plato plato : platos) {
                PlatosJSON elem = new PlatosJSON();
                elem.setNombre(plato.getNombre());
                elem.setPreparacion(plato.getPreparacion());
                elem.setIngredientes(plato.getIngredientes());
                elem.setCantidades(plato.getCantidades());
                elem.setCalorias(plato.getCalorias());
                elem.setCalorias_total(plato.getCaloriasTot().toString());
                list.add(elem);
            }
        }
        return list;
    }

    public RespJSON getReunion(String nombre, Integer mes, Integer dia) {
        RespJSON resp = new RespJSON();
        Dia newdia = new Dia();
        newdia.setNombreusuario(nombre);
        newdia.setDia(dia);
        newdia.setMes(mes);
        Optional<Dia> diaid = diaRepo.findOne(Example.of(newdia));
        Boolean reunion = diaid.get().getReunion();
        resp.setResp(reunion);
        return resp;
    }

}
