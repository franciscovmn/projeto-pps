package br.edu.ifpb.pps.relatorios;

import java.util.List;
import java.util.Map;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

public class RelatorioHtml extends GeradorRelatorio {
    @Override
    protected void montarCabecalho(
            StringBuilder sb,
            String nomeEvento,
            int submetidos,
            int avaliados,
            int revisores,
            int pendentes
    ) {
        sb.append("""
            <html>
            <head>
                <meta charset="UTF-8">
            """);
        sb.append("    <title>Relatório - ").append(nomeEvento).append("</title>\n"); // Incluído no título da página
        sb.append("""
            </head>
            <body>
            """);

        sb.append("<h1>Relatório do Evento: ").append(nomeEvento).append("</h1>").append("\n"); // Incluído no h1
        sb.append("<ul>").append("\n");
        sb.append("<li>Artigos submetidos: ").append(submetidos).append("</li>").append("\n");
        sb.append("<li>Artigos avaliados: ").append(avaliados).append("</li>").append("\n");
        sb.append("<li>Quantidade de revisores: ").append(revisores).append("</li>").append("\n");
        sb.append("<li>Artigos pendentes: ").append(pendentes).append("</li>").append("\n");
        sb.append("</ul>").append("\n\n");
    }

    @Override
    protected void montarCorpo(
            StringBuilder sb,
            Map<Artigo, List<PerfilRevisor>> pendencias
    ) {
        sb.append("<h2>Artigos Pendentes</h2>").append("\n");

        for (Map.Entry<Artigo, List<PerfilRevisor>> entry : pendencias.entrySet()) {
            sb.append("<h3>")
                    .append(entry.getKey().getTitulo())
                    .append("</h3>");

            sb.append("<ul>");
            for (PerfilRevisor revisor : entry.getValue()) {
                sb.append("<li>")
                        .append(revisor.getPesquisador().getNome())
                        .append("</li>");
            }
            sb.append("</ul>");
        }
    }

    @Override
    protected void montarRodape(StringBuilder sb) {
        sb.append("""
                <hr>
                <p>Relatório gerado automaticamente.</p>
                </body>
                </html>
                """);
    }
}