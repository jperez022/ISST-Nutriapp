package com.isst.nutriapp.grupo18.apirest.usuario;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/nuevo/{nombre}")
    public ResponseEntity<Void> createUsuario(@PathVariable("nombre") String nombre) {
        usuarioService.createUsuario(nombre);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/crear_objetivos/{nombre}/{peso}/{ejercicio}")
    public ResponseEntity<Void> createObjetivos(@PathVariable("nombre") String nombre, @PathVariable("peso") String peso, @PathVariable("ejercicio") String ejercicio) {
        if (!usuarioService.usuarioExiste(nombre)) {
            return ResponseEntity.status(400).body(null);
        }
        usuarioService.createObjetivos(nombre, peso, ejercicio);
        return ResponseEntity.ok(null);
    }

    @GetMapping("obtener_objetivos/{nombre}")
    public ResponseEntity<ObjetivosJSON> obtenerObjetivos(@PathVariable("nombre") String nombre) {
        if (!usuarioService.usuarioExiste(nombre)) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(usuarioService.obtenerObjetivos(nombre));
    }

}
