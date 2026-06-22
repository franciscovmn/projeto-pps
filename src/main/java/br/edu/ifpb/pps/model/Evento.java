package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.enums.StatusEvento;

public class Evento {
    private String nome;
    private String cidade;
    private String periodo;
    private CategoriaEvento categoriaEvento;
    private StatusEvento statusEvento; //TO-DO: trocar por padrão State

    public Evento(String nome, String cidade, String periodo, CategoriaEvento categoriaEvento) {
        this.nome = nome;
        this.cidade = cidade;
        this.periodo = periodo;
        this.categoriaEvento = categoriaEvento;
        this.statusEvento = StatusEvento.ABERTO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public StatusEvento getStatusEvento() {
        return statusEvento;
    }

    public void setStatusEvento(StatusEvento statusEvento) {
        this.statusEvento = statusEvento;
    }
}
