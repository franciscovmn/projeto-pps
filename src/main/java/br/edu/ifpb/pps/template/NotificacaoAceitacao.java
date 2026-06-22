package br.edu.ifpb.pps.template;

import java.util.List;

public class NotificacaoAceitacao extends NotificacaoEmail {

    public NotificacaoAceitacao(String nomeAutor, String numeroArtigo, String titulo,
                                String evento, String nomeCoordenador, List<String[]> pareceres) {
        super(nomeAutor, numeroArtigo, titulo, evento, nomeCoordenador, pareceres);
    }

    @Override
    protected String cabecalho() {
        return "Prezado(a) Sr(a). " + nomeAutor + ":\n\n"
                + "Parabéns! Sua submissão de nº " + numeroArtigo
                + ", intitulada \"" + titulo + "\", para o " + evento + ", foi aceita.\n\n"
                + "As avaliações estão disponíveis ao final do e-mail.";
    }
}
