package com.isst.nutriapp.grupo18.apirest.dia;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isst.nutriapp.grupo18.apirest.JSON.DiaJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.PlatosJSON;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/calendario")
@RequiredArgsConstructor
public class DiaController {

    private final DiaService diaService;

    @GetMapping("/crear/{nombre}")
    public ResponseEntity<Void> createCalendario(@PathVariable("nombre") String nombre) {
        if (!diaService.usuarioExiste(nombre)) {
            return ResponseEntity.status(400).body(null);
        }
        diaService.createCalendario(nombre);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/dia/{nombre}/{mes}/{dia}")
    public ResponseEntity<List<PlatosJSON>> getPlatosDia(@PathVariable("nombre") String nombre, @PathVariable("mes") Integer mes, @PathVariable("dia") Integer dia) {
        if (!diaService.usuarioExiste(nombre)) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(diaService.getPlatosDia(nombre, mes, dia));
    }

    @GetMapping("/dia/platos/{nombre}")
    public ResponseEntity<List<DiaJSON>> getDias(@PathVariable("nombre") String nombre) {
        if (!diaService.usuarioExiste(nombre)) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(diaService.getDias(nombre));
    }

}
