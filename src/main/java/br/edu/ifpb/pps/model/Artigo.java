package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.StatusArtigo;

import java.util.ArrayList;
import java.util.List;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public List<Pesquisador> getCoAutores() {
        return coAutores;
    }

    public void setCoAutores(List<Pesquisador> coAutores) {
        this.coAutores = coAutores;
    }

    public List<AreaTematica> getAreasTematicas() {
        return areasTematicas;
    }

    public void setAreasTematicas(List<AreaTematica> areasTematicas) {
        this.areasTematicas = areasTematicas;
    }

    public StatusArtigo getStatusArtigo() {
        return statusArtigo;
    }

    public void setStatusArtigo(StatusArtigo statusArtigo) {
        this.statusArtigo = statusArtigo;
    }
}