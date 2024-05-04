package com.isst.nutriapp.grupo18.apirest.articulo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isst.nutriapp.grupo18.apirest.JSON.NuevoArticuloJSON;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/articulo")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @GetMapping("/obtener")
    public ResponseEntity<List<Articulo>> getArticulos() {
        return ResponseEntity.ok(articuloService.getArticulos());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Void> createArticulos(@RequestBody NuevoArticuloJSON articuloJSON) {
        String titulo = articuloJSON.getTitulo();
        String descripcion = articuloJSON.getDescripcion();
        String url = articuloJSON.getUrl();
        articuloService.createArticulos(descripcion, titulo, url);
        return ResponseEntity.ok(null);
    }

}
