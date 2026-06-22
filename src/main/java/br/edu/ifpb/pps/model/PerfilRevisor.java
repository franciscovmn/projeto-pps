package br.edu.ifpb.pps.model;

import java.util.ArrayList;
import java.util.List;

public class PerfilRevisor {
    private Pesquisador pesquisador;
    private List<AreaTematica> afinidades = new ArrayList<AreaTematica>();
    private List<Artigo> artigosRevisados;

    public PerfilRevisor(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public PerfilRevisor(Pesquisador pesquisador, List<AreaTematica> afinidades) {
        this.pesquisador = pesquisador;
        this.afinidades = afinidades;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public List<AreaTematica> getAfinidades() {
        return afinidades;
    }

    public void setAfinidades(List<AreaTematica> afinidades) {
        this.afinidades = afinidades;
    }

    public List<Artigo> getArtigosRevisados() {
        return artigosRevisados;
    }

    public void setArtigosRevisados(List<Artigo> artigosRevisados) {
        this.artigosRevisados = artigosRevisados;
    }
}
