package br.edu.ifpb.pps.relatorios;

import br.edu.ifpb.pps.dashboard.Dashboard;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public abstract class GeradorRelatorio {
    public final void gerarRelatorio(String caminhoArquivo) throws IOException {
        Dashboard dashboard = Dashboard.getInstance();

        String nomeEvento = dashboard.getEvento().getNome();
        int artigosSubmetidos = dashboard.getArtigosSubmetidos();
        int artigosAvaliados = dashboard.getArtigosAvaliados();
        int qtdeRevisores = dashboard.getQtdePerfilRevisores();
        int artigosPendentes = dashboard.getQuantidadeArtigosPendentes();
        Map<Artigo, List<PerfilRevisor>> pendencias = dashboard.getArtigosPendentes();

        StringBuilder sb = new StringBuilder();

        montarCabecalho(
                sb,
                nomeEvento,
                artigosSubmetidos,
                artigosAvaliados,
                qtdeRevisores,
                artigosPendentes
        );

        montarCorpo(
                sb,
                pendencias
        );

        montarRodape(sb);

        salvarArquivo(sb.toString(), caminhoArquivo);
    }

    protected abstract void montarCabecalho(StringBuilder sb, String nomeEvento, int artigosSubmetidos,int artigosAvaliados,int qtdeRevisores,int artigosPendentes);

    protected abstract void montarCorpo(StringBuilder sb, Map<Artigo, List<PerfilRevisor>> pendencias);

    protected abstract void montarRodape(StringBuilder sb);

    private void salvarArquivo(String conteudo, String caminho) throws IOException {
        Files.writeString(
                Path.of(caminho),
                conteudo,
                StandardCharsets.UTF_8
        );
    }
}