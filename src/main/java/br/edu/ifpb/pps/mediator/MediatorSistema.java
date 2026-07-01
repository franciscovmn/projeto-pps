package br.edu.ifpb.pps.mediator;

import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;
import br.edu.ifpb.pps.modulos.ModuloEvento;
import br.edu.ifpb.pps.modulos.ModuloSubmissaoArtigo;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;

import java.util.List;

public class MediatorSistema {

    private final ModuloCadastroPesquisador moduloCadastro;
    private final ModuloEvento moduloEvento;
    private final ModuloSubmissaoArtigo moduloSubmissao;
    private final ServicoNotificacao servicoNotificacao;

    public MediatorSistema(ModuloCadastroPesquisador moduloCadastro, ModuloEvento moduloEvento,
                           ModuloSubmissaoArtigo moduloSubmissao, ServicoNotificacao servicoNotificacao) {
        this.moduloCadastro = moduloCadastro;
        this.moduloEvento = moduloEvento;
        this.moduloSubmissao = moduloSubmissao;
        this.servicoNotificacao = servicoNotificacao;

        moduloCadastro.setMediator(this);
        moduloEvento.setMediator(this);
        moduloSubmissao.setMediator(this);
    }

    public void cadastrarAreaTematica(String descricao) {
        moduloEvento.cadastrarAreaTematica(descricao);
    }

    public void convidarRevisor(String email) {
        moduloCadastro.convidarRevisor(email);
    }

    public Artigo submeterArtigo(String titulo, Pesquisador autor, String resumo, String nomePDF,
                                 List<AreaTematica> areasTematicas) {
        return moduloSubmissao.submeter(titulo, autor, resumo, nomePDF, areasTematicas);
    }

    public void iniciarEvento() {
        moduloEvento.abrirEvento();
    }

    public void encerrarEvento() {
        moduloEvento.fecharEvento();
    }

    public void notificar(String destinatario, String assunto, String mensagem) {
        servicoNotificacao.enviar(destinatario, assunto, mensagem);
    }

    public ModuloCadastroPesquisador getModuloCadastro() {
        return moduloCadastro;
    }

    public ModuloEvento getModuloEvento() {
        return moduloEvento;
    }

    public ModuloSubmissaoArtigo getModuloSubmissao() {
        return moduloSubmissao;
    }
}
