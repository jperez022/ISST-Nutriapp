package com.isst.nutriapp.grupo18.apirest.plato;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isst.nutriapp.grupo18.apirest.JSON.NuevoPlatoJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.NuevoPlatoSugeridoJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.PlatosJSON;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    @PostMapping("/agregar")
    public ResponseEntity<Void> agregarPlato(@RequestBody NuevoPlatoJSON platoJSON) {
        String usuario = platoJSON.getUsuario();
        if (!platoService.usuarioExiste(usuario)) {
            return ResponseEntity.status(400).body(null);
        }
        String nombre = platoJSON.getNombre();
        String preparacion = platoJSON.getPreparacion();
        String ingredientes = platoJSON.getIngredientes();
        String descripcion = platoJSON.getDescripcion();
        String calorias = platoJSON.getCalorias();
        Integer calorias_total = platoJSON.getCalorias_total();
        String dia_mes = platoJSON.getDia_mes();
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
    public ResponseEntity<List<PlatosJSON>> getPlatosusuario(@PathVariable("usuario") String usuario) {
        if (!platoService.usuarioExiste(usuario)) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(platoService.getPlatosusuario(usuario));
    }

    @PostMapping("/agregarSugerido")
    public ResponseEntity<Void> createPlatoSugerido(@RequestBody NuevoPlatoSugeridoJSON platoSugeridoJSON) {
        String nombre = platoSugeridoJSON.getNombre();
        String preparacion = platoSugeridoJSON.getPreparacion();
        String ingredientes = platoSugeridoJSON.getIngredientes();
        String cantidades = platoSugeridoJSON.getCantidades();
        String calorias = platoSugeridoJSON.getCalorias();
        Integer calorias_total = platoSugeridoJSON.getCalorias_total();
        platoService.createPlatoSugerido(nombre, preparacion, ingredientes, cantidades, calorias, calorias_total);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/obtenerSugerido/{calorias_total}")
    public ResponseEntity<List<PlatosJSON>> obtenerPlatoSugerido(@PathVariable("calorias_total") String calorias_total) {
        return ResponseEntity.ok(platoService.obtenerPlatoSugerido(calorias_total));
    }
}
