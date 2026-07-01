package br.edu.ifpb.pps.mediator;

import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;
import br.edu.ifpb.pps.modulos.ModuloEvento;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;

public class MediatorSistema {

    private final ModuloCadastroPesquisador moduloCadastro;
    private final ModuloEvento moduloEvento;
    private final ServicoNotificacao servicoNotificacao;

    public MediatorSistema(ModuloCadastroPesquisador moduloCadastro, ModuloEvento moduloEvento,
                           ServicoNotificacao servicoNotificacao) {
        this.moduloCadastro = moduloCadastro;
        this.moduloEvento = moduloEvento;
        this.servicoNotificacao = servicoNotificacao;

        moduloCadastro.setMediator(this);
        moduloEvento.setMediator(this);
    }

    public void cadastrarAreaTematica(String descricao) {
        moduloEvento.cadastrarAreaTematica(descricao);
    }

    public void convidarRevisor(String email) {
        moduloCadastro.convidarRevisor(email);
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
}
