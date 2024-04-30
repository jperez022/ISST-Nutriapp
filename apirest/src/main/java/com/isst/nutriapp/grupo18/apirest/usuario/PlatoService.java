package com.isst.nutriapp.grupo18.apirest.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository platoRepo;
    private final UsuarioRepository usuarioRepo;
    private final DiaRepository diaRepo;

    public boolean usuarioExiste(String usuario) {
        return usuarioRepo.existsByNombre(usuario);
    }

    public void agregarPlato(String usuario, String nombre, String preparacion, 
        String ingredientes, String cantidades, String calorias, Integer calorias_total, String dia_mes) {
            nombre = nombre.replace('_',' ');
            preparacion = preparacion.replace('_',' ');
            ingredientes = ingredientes.replace('_',' ').replace('-','/');
            cantidades = cantidades.replace('_',' ').replace('-','/');
            calorias = calorias.replace('_',' ').replace('-','/');
            Plato plato = new Plato();
            plato.setNombre(nombre);
            if (preparacion != " ") {
                plato.setPreparacion(preparacion);
            }
            plato.setIngredientes(ingredientes);
            if (cantidades != " ") {
                plato.setCantidades(cantidades);
            }
            plato.setCalorias(calorias);
            plato.setCaloriasTot(calorias_total);
            plato.setSugerido(false);
            if (!platoRepo.exists(Example.of(plato))) {
                plato = platoRepo.save(plato);
            }
            String[] diaymes = dia_mes.split("_");
            Integer midia = Integer.parseInt(diaymes[0]);
            Integer mimes = Integer.parseInt(diaymes[1]);
            Dia dia = new Dia();
            dia.setNombreusuario(usuario);
            dia.setDia(midia);
            dia.setMes(mimes);
            Optional<Plato> platoid = platoRepo.findOne(Example.of(plato));
            Optional<Dia> diaid = diaRepo.findOne(Example.of(dia));
            List<Dia> dias = platoid.get().getDias();
            if (dias == null) {
                dias = new ArrayList<Dia>();
            }
            dias.add(diaid.get());
            platoid.get().setDias(dias);
            platoRepo.save(platoid.get());
    }

    public void modificarPlato(String usuario, String nombre, String preparacion, 
        String ingredientes, String calorias, Integer calorias_total, String dia_mes) {
            String[] diaymes = dia_mes.split("_");
            Integer midia = Integer.parseInt(diaymes[0]);
            Integer mimes = Integer.parseInt(diaymes[1]);
            Dia dia = new Dia();
            dia.setNombreusuario(usuario);
            dia.setDia(midia);
            dia.setMes(mimes);
            Optional<Dia> diaid = diaRepo.findOne(Example.of(dia));
            List<Plato> posibles = platoRepo.findByDias_Id(diaid.get().getId());
            nombre = nombre.replace('_',' ');
            preparacion = preparacion.replace('_',' ');
            ingredientes = ingredientes.replace('_',' ').replace('-','/');
            calorias = calorias.replace('_',' ').replace('-','/');
            for (Plato plato : posibles) {
                if ((plato.getNombre().equals(nombre)) && (plato.getIngredientes().equals(ingredientes)) 
                    && (plato.getCalorias().equals(calorias)) && (plato.getCaloriasTot() == calorias_total)) {
                        plato.setPreparacion(preparacion);
                        platoRepo.save(plato);
                        break;
                }
            }
    }

    public PlatosJSON getPlatosusuario(String usuario) {
        PlatosJSON resp = new PlatosJSON();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        Dia edia = new Dia();
        edia.setNombreusuario(usuario);
        List<Dia> dias = diaRepo.findAll(Example.of(edia));
        for(Dia dia : dias) {
            if (dia.getPlatos() != null) {
                List<Plato> platos = dia.getPlatos();
                for (Plato plato : platos) {
                    ArrayList<String> elem = new ArrayList<String>();
                    elem.add(plato.getNombre());
                    elem.add(plato.getPreparacion());
                    elem.add(plato.getIngredientes());
                    elem.add(plato.getCantidades());
                    elem.add(plato.getCalorias());
                    elem.add(plato.getCaloriasTot().toString());
                    if (!list.contains(elem)) {
                        list.add(elem);
                    }
                }
            }
        }
        if (list.isEmpty()) {
            ArrayList<String> vacio = new ArrayList<String>();
            vacio.add("vacio");
            list.add(vacio);
        }
        resp.setResp(list);
        return resp;
    }

    public void createPlatoSugerido(String nombre, String preparacion, String ingredientes, 
        String cantidades, String calorias, Integer calorias_total) {
            nombre = nombre.replace('_',' ');
            preparacion = preparacion.replace('_',' ');
            ingredientes = ingredientes.replace('_',' ').replace('-','/');
            cantidades = cantidades.replace('_',' ').replace('-','/');
            calorias = calorias.replace('_',' ').replace('-','/');
            Plato plato = new Plato();
            plato.setNombre(nombre);
            plato.setPreparacion(preparacion);
            plato.setIngredientes(ingredientes);
            plato.setCantidades(cantidades);
            plato.setCalorias(calorias);
            plato.setCaloriasTot(calorias_total);
            plato.setSugerido(true);
            platoRepo.save(plato);
    }

    public PlatosJSON obtenerPlatoSugerido(String calorias_total) {
        PlatosJSON resp = new PlatosJSON();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        if (calorias_total.equals("no")) {
            Plato eplato = new Plato();
            eplato.setSugerido(true);
            List<Plato> platos = platoRepo.findAll(Example.of(eplato));
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
        } else {
            String [] caloriastotal = calorias_total.split("_");
            Integer calbaja = Integer.parseInt(caloriastotal[0]);
            Integer calalta = Integer.parseInt(caloriastotal[1]);
            List<Plato> platos = platoRepo.findByCaloriasTotLessThanEqualAndCaloriasTotGreaterThanEqualAndSugerido(calalta, calbaja, true);
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
        }
        resp.setResp(list);
        return resp;
    }

}
