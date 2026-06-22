package br.edu.ifpb.pps.template;

import java.util.List;

public abstract class NotificacaoEmail {

    protected String nomeAutor;
    protected String numeroArtigo;
    protected String titulo;
    protected String evento;
    protected String nomeCoordenador;
    protected List<String[]> pareceres;

    public NotificacaoEmail(String nomeAutor, String numeroArtigo, String titulo,
                            String evento, String nomeCoordenador, List<String[]> pareceres) {
        this.nomeAutor = nomeAutor;
        this.numeroArtigo = numeroArtigo;
        this.titulo = titulo;
        this.evento = evento;
        this.nomeCoordenador = nomeCoordenador;
        this.pareceres = pareceres;
    }

    public final String gerarNotificacao() {
        return cabecalho()
                + "\n\n" + atenciosamente()
                + "\n\n" + parecerRevisores();
    }

    protected abstract String cabecalho();

    protected String atenciosamente() {
        return "Atenciosamente,\n\n"
                + nomeCoordenador + "\n"
                + "Coordenador(a) do Comitê de Programa do " + evento;
    }

    protected String parecerRevisores() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pareceres.size(); i++) {
            String[] parecer = pareceres.get(i);
            sb.append("[Revisor ").append(i + 1).append("]\n");
            sb.append("Principal Contribuição ou pontos positivos\n");
            sb.append("================================\n");
            sb.append(parecer[0]).append("\n\n");
            sb.append("Pontos negativos\n");
            sb.append("================================\n");
            for (int j = 1; j < parecer.length; j++) {
                sb.append("N").append(j).append(" - ").append(parecer[j]).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }
}
