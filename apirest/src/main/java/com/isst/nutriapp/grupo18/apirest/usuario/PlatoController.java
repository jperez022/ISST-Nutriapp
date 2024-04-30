package com.isst.nutriapp.grupo18.apirest.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    @GetMapping("/agregar/{usuario}/{nombre}/{preparacion}/{ingredientes}/{descripcion}/{calorias}/{calorias_total}/{dia_mes}")
    public ResponseEntity<Void> agregarPlato(@PathVariable("usuario") String usuario, 
        @PathVariable("nombre") String nombre, 
        @PathVariable("preparacion") String preparacion, 
        @PathVariable("ingredientes") String ingredientes, 
        @PathVariable("descripcion") String descripcion, 
        @PathVariable("calorias") String calorias, 
        @PathVariable("calorias_total") Integer calorias_total, 
        @PathVariable("dia_mes") String dia_mes) {
            if (!platoService.usuarioExiste(usuario)) {
                return ResponseEntity.status(400).body(null);
            }
            platoService.agregarPlato(usuario, nombre, preparacion, ingredientes, descripcion, calorias, calorias_total, dia_mes);
            return ResponseEntity.ok(null);
    }

    @GetMapping("/modificar/{usuario}/{nombre}/{preparacion}/{ingredientes}/{calorias}/{calorias_total}/{dia_mes}")
    public ResponseEntity<Void> modificarPlato(@PathVariable("usuario") String usuario, 
    @PathVariable("nombre") String nombre, 
    @PathVariable("preparacion") String preparacion, 
    @PathVariable("ingredientes") String ingredientes, 
    @PathVariable("calorias") String calorias, 
    @PathVariable("calorias_total") Integer calorias_total, 
    @PathVariable("dia_mes") String dia_mes) {
        if (!platoService.usuarioExiste(usuario)) {
            return ResponseEntity.status(400).body(null);
        }
        platoService.modificarPlato(usuario, nombre, preparacion, ingredientes, calorias, calorias_total, dia_mes);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/obtener/{usuario}")
    public ResponseEntity<PlatosJSON> getPlatosusuario(@PathVariable("usuario") String usuario) {
        if (!platoService.usuarioExiste(usuario)) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(platoService.getPlatosusuario(usuario));
    }

    @GetMapping("/agregarSugerido/{nombre}/{preparacion}/{ingredientes}/{cantidades}/{calorias}/{calorias_total}")
    public ResponseEntity<Void> createPlatoSugerido(@PathVariable("nombre") String nombre, 
        @PathVariable("preparacion") String preparacion, 
        @PathVariable("ingredientes") String ingredientes, 
        @PathVariable("cantidades") String cantidades, 
        @PathVariable("calorias") String calorias, 
        @PathVariable("calorias_total") Integer calorias_total) {
            platoService.createPlatoSugerido(nombre, preparacion, ingredientes, cantidades, calorias, calorias_total);
            return ResponseEntity.ok(null);
        }

    @GetMapping("/obtenerSugerido/{calorias_total}")
    public ResponseEntity<PlatosJSON> obtenerPlatoSugerido(@PathVariable("calorias_total") String calorias_total) {
        return ResponseEntity.ok(platoService.obtenerPlatoSugerido(calorias_total));
    }
}
