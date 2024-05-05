package com.isst.nutriapp.grupo18.apirest.especialista;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isst.nutriapp.grupo18.apirest.JSON.NuevoEspecialistaJSON;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/especialista")
@RequiredArgsConstructor
public class EspecialistaController {

    private final EspecialistaService especialistaService;

    @GetMapping("/obtener")
    public ResponseEntity<List<Especialista>> getEspecialistas() {
        return ResponseEntity.ok(especialistaService.getEspecialistas());
    }

    @PostMapping("/valoracion/{nombre}/{valoracion}")
    public ResponseEntity<Void> setValoracion(@PathVariable("nombre") String nombre, @PathVariable("valoracion") Integer valoracion) {
        especialistaService.setValoracion(nombre, valoracion);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Void> createEspecialista(@RequestBody NuevoEspecialistaJSON especialistaJSON) {
        String nombre = especialistaJSON.getNombre();
        Integer movil = especialistaJSON.getMovil();
        String info = especialistaJSON.getInfo();
        Integer precio = especialistaJSON.getPrecio();
        especialistaService.createEspecialista(nombre, movil, info, precio);
        return ResponseEntity.ok(null);
    }

}
