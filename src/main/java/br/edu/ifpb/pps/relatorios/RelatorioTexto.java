package br.edu.ifpb.pps.relatorios;

import java.util.Map;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.util.List;

public class RelatorioTexto extends GeradorRelatorio {
    @Override
    protected void montarCabecalho(
            StringBuilder sb,
            String nomeEvento,
            int submetidos,
            int avaliados,
            int revisores,
            int pendentes
    ) {
        sb.append("=====================================\n");
        sb.append("RELATÓRIO DO EVENTO: ").append(nomeEvento.toUpperCase()).append("\n");
        sb.append("=====================================\n\n");

        sb.append("Artigos submetidos: ").append(submetidos).append("\n");
        sb.append("Artigos avaliados: ").append(avaliados).append("\n");
        sb.append("Quantidade de revisores: ").append(revisores).append("\n");
        sb.append("Artigos pendentes: ").append(pendentes).append("\n\n");
    }

    @Override
    protected void montarCorpo(
            StringBuilder sb,
            Map<Artigo, List<PerfilRevisor>> pendencias
    ) {
        sb.append("Artigos Pendentes\n");
        sb.append("-----------------\n");

        for (Map.Entry<Artigo, List<PerfilRevisor>> entry : pendencias.entrySet()) {
            sb.append(entry.getKey().getTitulo()).append("\n");

            for (PerfilRevisor revisor : entry.getValue()) {

                sb.append("   - ")
                        .append(revisor.getPesquisador().getNome())
                        .append("\n");
            }
        }
    }

    @Override
    protected void montarRodape(StringBuilder sb) {
        sb.append("\n=====================================\n");
        sb.append("Fim do relatório.");
    }
}