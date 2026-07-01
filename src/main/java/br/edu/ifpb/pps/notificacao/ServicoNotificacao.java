package br.edu.ifpb.pps.notificacao;

public interface ServicoNotificacao {
    void enviar(String destinatario, String assunto, String mensagem);
}
