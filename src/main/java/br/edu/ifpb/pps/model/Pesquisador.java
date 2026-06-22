package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.PapelUsuario;

import java.util.ArrayList;
import java.util.List;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public List<PapelUsuario> getPapeisUsuario() {
        return papeisUsuario;
    }

    public void setPapeisUsuario(List<PapelUsuario> papeisUsuario) {
        this.papeisUsuario = papeisUsuario;
    }

    public List<AreaTematica> getAfinidades() {
        return afinidades;
    }

    public void setAfinidades(List<AreaTematica> afinidades) {
        this.afinidades = afinidades;
    }

    public List<Artigo> getArtigosSubmetidos() {
        return artigosSubmetidos;
    }

    public void setArtigosSubmetidos(List<Artigo> artigosSubmetidos) {
        this.artigosSubmetidos = artigosSubmetidos;
    }
}
