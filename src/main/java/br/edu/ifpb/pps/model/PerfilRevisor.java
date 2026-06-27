package br.edu.ifpb.pps.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilRevisor {
    private Pesquisador pesquisador;
    private List<AreaTematica> afinidades = new ArrayList<AreaTematica>();
    private List<Artigo> artigos = new ArrayList<Artigo>();
    private List<Artigo> artigosRevisados = new ArrayList<Artigo>();

    public PerfilRevisor(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public PerfilRevisor(Pesquisador pesquisador, List<AreaTematica> afinidades) {
        this.pesquisador = pesquisador;
        this.afinidades = afinidades;
    }
}
