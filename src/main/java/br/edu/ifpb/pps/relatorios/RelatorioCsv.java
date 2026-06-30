package br.edu.ifpb.pps.relatorios;

import java.util.Map;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.util.List;

public class RelatorioCsv extends GeradorRelatorio {

    @Override
    protected void montarCabecalho(
            StringBuilder sb,
            String nomeEvento,
            int submetidos,
            int avaliados,
            int revisores,
            int pendentes
    ) {
        sb.append("Evento,").append(nomeEvento).append("\n\n");
        sb.append("Indicador,Valor\n");

        sb.append("Artigos Submetidos,").append(submetidos).append("\n");
        sb.append("Artigos Avaliados,").append(avaliados).append("\n");
        sb.append("Quantidade Revisores,").append(revisores).append("\n");
        sb.append("Artigos Pendentes,").append(pendentes).append("\n\n");

        sb.append("Artigo,Revisor\n");
    }

    @Override
    protected void montarCorpo(
            StringBuilder sb,
            Map<Artigo, List<PerfilRevisor>> pendencias
    ) {
        for (Map.Entry<Artigo, List<PerfilRevisor>> entry : pendencias.entrySet()) {

            for (PerfilRevisor revisor : entry.getValue()) {
                sb.append('"')
                        .append(entry.getKey().getTitulo())
                        .append('"')
                        .append(',');
                sb.append('"')
                        .append(revisor.getPesquisador().getNome())
                        .append('"')
                        .append("\n");

            }
        }
    }

    @Override
    protected void montarRodape(StringBuilder sb) {
        sb.append("\n# Relatório gerado automaticamente.");
    }
}