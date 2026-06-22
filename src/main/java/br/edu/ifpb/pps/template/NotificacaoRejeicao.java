package br.edu.ifpb.pps.template;

import java.util.List;

public class NotificacaoRejeicao extends NotificacaoEmail {

    public NotificacaoRejeicao(String nomeAutor, String numeroArtigo, String titulo,
                               String evento, String nomeCoordenador, List<String[]> pareceres) {
        super(nomeAutor, numeroArtigo, titulo, evento, nomeCoordenador, pareceres);
    }

    @Override
    protected String cabecalho() {
        return "Prezado(a) Sr(a). " + nomeAutor + ":\n\n"
                + "Lamentamos informar que seu artigo de nº " + numeroArtigo
                + " intitulado \"" + titulo + "\" não pôde ser aceito para o " + evento + ".\n\n"
                + "Ao final do email, seguem os pareceres dos revisores, que esperamos que possam "
                + "auxiliá-lo(a) em futuras submissões.\n\n"
                + "Agradecemos sua submissão.";
    }
}
