package br.edu.ifpb.pps.relatorios;

import br.edu.ifpb.pps.csv.CarregadorDadosCsv;
import br.edu.ifpb.pps.dashboard.Dashboard;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//atenção: essa classe é pra teste, não pra uso de vdd por enquanto.
public class TesteRelatorio {

    public static void main(String[] args) {
        try {
            CarregadorDadosCsv loader = CarregadorDadosCsv.getInstance();

            Dashboard dashboard = Dashboard.getInstance();
            dashboard.zerarDashboard();

            // Recupera o primeiro evento carregado do CSV e define no Dashboard
            if (!loader.getEventos().isEmpty()) {
                dashboard.setEvento(loader.getEventos().get(0));
            } else {
                System.out.println("Aviso: Nenhum evento foi encontrado no CSV.");
            }

            // Quantidade de revisores
            dashboard.setQtdePerfilRevisores(loader.getPerfis().size());

            // Marca todos os artigos como pendentes de avaliação
            for (Artigo artigo : loader.getArtigos()) {

                // utiliza os dois revisores
                List<PerfilRevisor> revisores = new ArrayList<>();

                for (int i = 0; i < Math.min(2, loader.getPerfis().size()); i++) {
                    revisores.add(loader.getPerfis().get(i));
                }

                dashboard.adicionarArtigoPendente(artigo, revisores);
            }

            // Marca o primeiro artigo como avaliado
            if (!loader.getArtigos().isEmpty()) {
                dashboard.registrarAvaliacaoConcluida(loader.getArtigos().get(0));
            }

            // ===== Geração dos relatórios =====
            String caminhoArquivo = "src/main/resources/relatorios";

            GeradorRelatorio relatorioTexto = new RelatorioTexto();
            relatorioTexto.gerarRelatorio(caminhoArquivo + "/relatorio.txt");

            GeradorRelatorio relatorioHtml = new RelatorioHtml();
            relatorioHtml.gerarRelatorio(caminhoArquivo + "/relatorio.html");

            GeradorRelatorio relatorioCsv = new RelatorioCsv();
            relatorioCsv.gerarRelatorio(caminhoArquivo + "/relatorio.csv");

            System.out.println("Relatórios gerados com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}