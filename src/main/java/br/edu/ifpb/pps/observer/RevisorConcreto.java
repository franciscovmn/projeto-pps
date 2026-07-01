package br.edu.ifpb.pps.observer;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

public class RevisorConcreto implements ObservadorRevisor {

    @Override
    public void atualizar(Artigo artigo, PerfilRevisor revisor) {
        System.out.println("    [Revisor notificado] " +
                revisor.getPesquisador().getNome() +
                " foi atribuído ao artigo: " + artigo.getTitulo());
    }
}
