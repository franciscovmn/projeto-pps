package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.StatusArtigo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Artigo {
    private String Id;
    private String titulo;
    private Pesquisador pesquisador;
    private List<Pesquisador> coAutores = new ArrayList<Pesquisador>();
    private List<AreaTematica> areasTematicas;
    private StatusArtigo statusArtigo; //TO-DO: trocar por padrão State

    public Artigo(String id, String titulo, Pesquisador pesquisador, List<AreaTematica> areasTematicas) {
        this.Id = id;
        this.titulo = titulo;
        this.pesquisador = pesquisador;
        this.areasTematicas = areasTematicas;
        this.statusArtigo = StatusArtigo.SUBMETIDO;
    }

    public Artigo(String id, String titulo, Pesquisador pesquisador,List<Pesquisador> coAutores, List<AreaTematica> areasTematicas) {
        this.Id = id;
        this.titulo = titulo;
        this.pesquisador = pesquisador;
        this.coAutores = coAutores;
        this.areasTematicas = areasTematicas;
        this.statusArtigo = StatusArtigo.SUBMETIDO;
    }
}