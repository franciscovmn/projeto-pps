package br.edu.ifpb.pps.observer;

import br.edu.ifpb.pps.apresentacao.ServicoApresentacao;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

public class RevisorConcreto implements ObservadorRevisor {

    private final ServicoApresentacao apresentacao;

    public RevisorConcreto(ServicoApresentacao apresentacao) {
        this.apresentacao = apresentacao;
    }

    @Override
    public void atualizar(Artigo artigo, PerfilRevisor revisor) {
        apresentacao.exibir("    [Revisor notificado] " +
                revisor.getPesquisador().getNome() +
                " foi atribuído ao artigo: " + artigo.getTitulo());
    }
}
