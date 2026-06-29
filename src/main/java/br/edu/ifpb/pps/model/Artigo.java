package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigo;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigoSubmetido;
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
    private String resumo;
    private String nomePDF;
    private List<Pesquisador> coAutores = new ArrayList<Pesquisador>();
    private List<AreaTematica> areasTematicas;
    private StatusArtigo statusArtigo;

    public Artigo(String id, String titulo, Pesquisador pesquisador, String resumo, String nomePDF, List<AreaTematica> areasTematicas) {
        this.Id = id;
        this.titulo = titulo;
        this.pesquisador = pesquisador;
        this.resumo = resumo;
        this.nomePDF = nomePDF;
        this.areasTematicas = areasTematicas;
        this.statusArtigo = new StatusArtigoSubmetido(this);
    }

    public Artigo(String id, String titulo, Pesquisador pesquisador, String resumo, String nomePDF, List<Pesquisador> coAutores, List<AreaTematica> areasTematicas) {
        this.Id = id;
        this.titulo = titulo;
        this.pesquisador = pesquisador;
        this.resumo = resumo;
        this.nomePDF = nomePDF;
        this.coAutores = coAutores;
        this.areasTematicas = areasTematicas;
        this.statusArtigo = new StatusArtigoSubmetido(this);
    }

    public String getParecer() {
        return statusArtigo.getParecer();
    }
}