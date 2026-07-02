package br.edu.ifpb.pps.observer;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;

public class EmailObservadorRevisor implements ObservadorRevisor {

    private final ServicoNotificacao servicoNotificacao;

    public EmailObservadorRevisor(ServicoNotificacao servicoNotificacao) {
        this.servicoNotificacao = servicoNotificacao;
    }

    @Override
    public void atualizar(Artigo artigo, PerfilRevisor revisor) {
        String destinatario = revisor.getPesquisador().getEmail();
        String assunto = "Convite para revisão: " + artigo.getTitulo();
        String corpo = "Prezado(a) " + revisor.getPesquisador().getNome() + ",\n\n"
                + "Você foi designado(a) para revisar o artigo \""
                + artigo.getTitulo() + "\".\n\n"
                + "Por favor, acesse o sistema para registrar seu parecer.\n\n"
                + "Sistema PPS — IFPB";

        servicoNotificacao.enviar(destinatario, assunto, corpo);
    }
}
