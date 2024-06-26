package com.isst.nutriapp.grupo18.apirest.reunion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isst.nutriapp.grupo18.apirest.JSON.NuevaReunionJSON;
import com.isst.nutriapp.grupo18.apirest.JSON.RespJSON;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reunion")
@RequiredArgsConstructor
public class ReunionController {

    private final ReunionService reunionService;

    @PostMapping("/nueva")
    public ResponseEntity<Void> nuevaReunion(@RequestBody NuevaReunionJSON reunionJSON) {
        String usuario = reunionJSON.getUsuario();
        if (!reunionService.usuarioExiste(usuario)) {
            return ResponseEntity.status(400).body(null);
        }
        String titulo = reunionJSON.getTitulo();
        String link = reunionJSON.getLink();
        String fecha = reunionJSON.getFecha();
        Integer dia = Integer.parseInt(fecha.split("_")[0]);
        Integer mes = Integer.parseInt(fecha.split("_")[1]);;
        String hora = reunionJSON.getHora();
        reunionService.nuevaReunion(titulo, link, dia, mes, hora, usuario);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/dia/{mes}/{dia}")
    public ResponseEntity<List<Reunion>> getReunionesDia(@PathVariable("mes") Integer mes, @PathVariable("dia") Integer dia) {
        return ResponseEntity.ok(reunionService.getReunionesDia(mes, dia));
    }

    @GetMapping("/hay/{mes}/{dia}")
    public ResponseEntity<RespJSON> getReunion(@PathVariable("mes") Integer mes, @PathVariable("dia") Integer dia) {
        return ResponseEntity.ok(reunionService.getReunion(mes, dia));
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<Reunion>> getDias() {
        return ResponseEntity.ok(reunionService.getDias());
    }

}
