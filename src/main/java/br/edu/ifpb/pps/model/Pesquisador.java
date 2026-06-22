package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.PapelUsuario;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pesquisador {
    private String nome;
    private String email;
    private String senha;
    private String instituicao;
    private List<PapelUsuario> papeisUsuario = new ArrayList<PapelUsuario>();
    private List<Artigo> artigosSubmetidos = new ArrayList<Artigo>();
    private List<AreaTematica> afinidades = new ArrayList<AreaTematica>();

    public Pesquisador(String nome, String email, String senha, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.instituicao = instituicao;
    }
}
