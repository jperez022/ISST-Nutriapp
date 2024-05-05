package com.isst.nutriapp.grupo18.apirest.reunion;

import java.sql.Time;
import java.util.List;

import org.springframework.stereotype.Service;

import com.isst.nutriapp.grupo18.apirest.usuario.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReunionService {

    private final ReunionRepository reunionRepo;
    private final UsuarioRepository usuarioRepo;

    public boolean usuarioExiste(String usuario) {
        return usuarioRepo.existsByNombre(usuario);
    }

    public void nuevaReunion(String titulo, String link, Integer dia, Integer mes, Time hora, String usuario) {
        Reunion reunion = new Reunion();
        reunion.setTitulo(titulo);
        reunion.setLink(link);
        reunion.setDia(dia);
        reunion.setMes(mes);
        reunion.setHora(hora);
        reunion.setUsuario(usuario);
        reunionRepo.save(reunion);
    }

    public List<Reunion> getReunionesDia(Integer mes, Integer dia) {
        return reunionRepo.findAll();
    }

}