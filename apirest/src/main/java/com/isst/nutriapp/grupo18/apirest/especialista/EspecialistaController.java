package com.isst.nutriapp.grupo18.apirest.especialista;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/nuevo/{nombre}/{movil}/{info}/{precio}")
    public ResponseEntity<Void> createEspecialista(@PathVariable String nombre, @PathVariable Integer movil, 
        @PathVariable String info, @PathVariable Integer precio) {
            especialistaService.createEspecialista(nombre, movil, info, precio);
            return ResponseEntity.ok(null);
    }

}
