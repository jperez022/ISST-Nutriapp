package com.isst.nutriapp.grupo18.apirest.reunion;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.isst.nutriapp.grupo18.apirest.JSON.RespJSON;
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

    public void nuevaReunion(String titulo, String link, Integer dia, Integer mes, String hora, String usuario) {
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
        Reunion reunion = new Reunion();
        reunion.setDia(dia);
        reunion.setMes(mes);
        return reunionRepo.findAll(Example.of(reunion));
    }

    public RespJSON getReunion(Integer mes, Integer dia) {
        RespJSON resp = new RespJSON();
        Reunion reunion = new Reunion();
        reunion.setDia(dia);
        reunion.setMes(mes);
        Boolean hay = reunionRepo.exists(Example.of(reunion));
        resp.setResp(hay);
        return resp;
    }

}
