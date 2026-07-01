package br.edu.ifpb.pps.modulos;

import br.edu.ifpb.pps.enums.PapelUsuario;
import br.edu.ifpb.pps.mediator.ModuloSistema;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.PerfilRevisor;
import br.edu.ifpb.pps.model.Pesquisador;

import java.util.*;

public class ModuloCadastroPesquisador extends ModuloSistema {
    private final Map<String, Pesquisador> pesquisadores = new HashMap<>();
    private final Map<String, PerfilRevisor> revisores = new HashMap<>();

    public void cadastrarPesquisador(Pesquisador pesquisador) {
        String email = pesquisador.getEmail();

        if (pesquisadores.containsKey(email)) {
            throw new RuntimeException("Já existe um pesquisador com esse e-mail.");
        }

        pesquisadores.put(email, pesquisador);
    }

    public void cadastrarPesquisador(String nome, String email, String senha, String instituicao, boolean ehCoordenador) {
        if (pesquisadores.containsKey(email)) {
            throw new RuntimeException("Já existe um pesquisador com esse e-mail.");
        }

        Pesquisador pesquisador = new Pesquisador(nome, email, senha, instituicao);
        pesquisador.getPapeisUsuario().add(PapelUsuario.AUTOR);

        if(ehCoordenador) {
            pesquisador.getPapeisUsuario().add(PapelUsuario.COORDENADOR);
        }

        pesquisadores.put(email, pesquisador);
    }

    public void cadastrarPesquisador(String nome, String email, String senha, String instituicao, boolean ehCoordenador, List<AreaTematica> afinidades) {
        if (pesquisadores.containsKey(email)) {
            throw new RuntimeException("Já existe um pesquisador com esse e-mail.");
        }

        Pesquisador pesquisador = new Pesquisador(nome, email, senha, instituicao);
        pesquisador.getPapeisUsuario().add(PapelUsuario.AUTOR);

        if(ehCoordenador) {
            pesquisador.getPapeisUsuario().add(PapelUsuario.COORDENADOR);
        }

        pesquisador.setAfinidades(afinidades);

        pesquisadores.put(email, pesquisador);
    }

    public Pesquisador buscarPesquisadorPorEmail(String email) {
        Pesquisador pesquisador = pesquisadores.get(email);

        if (pesquisador == null) {
            throw new RuntimeException("Pesquisador não encontrado.");
        }

        return pesquisador;
    }

    public Pesquisador autenticarPesquisador(String email, String senha) {
        Pesquisador pesquisador = buscarPesquisadorPorEmail(email);

        if (!pesquisador.getSenha().equals(senha)) {
            throw new RuntimeException("Credenciais inválidas.");
        }

        return pesquisador;
    }

    public void convidarRevisor(String email) {

        Pesquisador pesquisador = buscarPesquisadorPorEmail(email);

        if (revisores.containsKey(email)) {
            throw new RuntimeException("Este pesquisador já é revisor.");
        }

        PerfilRevisor perfil = new PerfilRevisor(
                pesquisador,
                pesquisador.getAfinidades());

        revisores.put(email, perfil);
    }

    public void convidarRevisor(String email, List<AreaTematica> afinidades) {

        Pesquisador pesquisador = buscarPesquisadorPorEmail(email);

        if (revisores.containsKey(email)) {
            throw new RuntimeException("Este pesquisador já é revisor.");
        }

        PerfilRevisor perfil = new PerfilRevisor(
                pesquisador,
                afinidades);

        revisores.put(email, perfil);
    }

    public PerfilRevisor buscarRevisorPorEmail(String email) {
        PerfilRevisor perfil = revisores.get(email);

        if (perfil == null) {
            throw new RuntimeException("Revisor não encontrado.");
        }

        return perfil;
    }

    public boolean ehRevisor(String email) {
        return revisores.containsKey(email);
    }

    public List<Pesquisador> listarPesquisadores() {
        return new ArrayList<>(pesquisadores.values());
    }

    public List<PerfilRevisor> listarRevisores() {
        return new ArrayList<>(revisores.values());
    }
}