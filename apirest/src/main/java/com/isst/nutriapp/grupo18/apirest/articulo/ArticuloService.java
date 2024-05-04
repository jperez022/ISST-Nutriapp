package com.isst.nutriapp.grupo18.apirest.articulo;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticuloService {

    private final ArticuloRepository articuloRepo;

    public List<Articulo> getArticulos() {
        return articuloRepo.findAll();
    }

    public void createArticulos(String descripcion, String titulo, String url) {
        Articulo articulo = new Articulo();
        articulo.setDescripcion(descripcion);
        articulo.setTitulo(titulo);
        articulo.setUrl(url);
        articuloRepo.save(articulo);
    }

}
