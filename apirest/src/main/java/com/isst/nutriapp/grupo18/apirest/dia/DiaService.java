package com.isst.nutriapp.grupo18.apirest.dia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.isst.nutriapp.grupo18.apirest.JSON.PlatosJSON;
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
                diaRepo.save(dia);
            }
            for (int i = 1; i < 30; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(2);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(3);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(4);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(5);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(6);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(7);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(8);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(9);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(10);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 31; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(11);
                diaRepo.save(dia);
            }
            for (int i = 1; i < 32; i++) {
                Dia dia = new Dia();
                dia.setNombreusuario(nombre);
                dia.setDia(i);
                dia.setMes(12);
                diaRepo.save(dia);
            }
        }
    }

    public PlatosJSON getPlatosDia(String nombre, String mes, String dia) {
        Integer midia = Integer.parseInt(dia);
        Integer mimes = Integer.parseInt(mes);
        Dia newdia = new Dia();
        newdia.setNombreusuario(nombre);
        newdia.setDia(midia);
        newdia.setMes(mimes);
        Optional<Dia> diaid = diaRepo.findOne(Example.of(newdia));
        List<Plato> platos = diaid.get().getPlatos();
        PlatosJSON resp = new PlatosJSON();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        if (!platos.isEmpty()) {
            for (Plato plato : platos) {
                ArrayList<String> elem = new ArrayList<String>();
                elem.add(plato.getNombre());
                elem.add(plato.getPreparacion());
                elem.add(plato.getIngredientes());
                elem.add(plato.getCantidades());
                elem.add(plato.getCalorias());
                elem.add(plato.getCaloriasTot().toString());
                list.add(elem);
            }
            resp.setResp(list);
            return resp;
        }
        ArrayList<String> vacio = new ArrayList<String>();
        vacio.add("vacio");
        list.add(vacio);
        resp.setResp(list);
        return resp;
    }

}
