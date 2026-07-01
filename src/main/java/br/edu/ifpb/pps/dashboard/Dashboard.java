package br.edu.ifpb.pps.dashboard;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Evento;
import br.edu.ifpb.pps.model.PerfilRevisor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Dashboard {
    private static Dashboard instance = new Dashboard();

    @Getter @Setter
    private Evento evento;
    @Getter
    private int artigosSubmetidos;
    @Getter
    private int artigosAvaliados;
    @Getter @Setter
    private int qtdePerfilRevisores;
    private final Map<Artigo, List<PerfilRevisor>> artigosPendentes = new HashMap<>(); // mapeia um Artigo para uma lista de revisores pendentes

    private Dashboard() {}

    public static Dashboard getInstance() {
        if (instance == null) {
            instance = new Dashboard();
        }
        return instance;
    }

    public void zerarDashboard() {
        this.artigosSubmetidos = 0;
        this.artigosAvaliados = 0;
        this.qtdePerfilRevisores = 0;
        this.artigosPendentes.clear();
    }

    public void incrementarArtigosSubmetidos() {
        this.artigosSubmetidos++;
    }

    public int getQuantidadeArtigosPendentes() {
        return artigosPendentes.size();
    }

    public Map<Artigo, List<PerfilRevisor>> getArtigosPendentes() {
        return artigosPendentes;
    }

    public void adicionarArtigoPendente(Artigo artigo, List<PerfilRevisor> revisores) {
        incrementarArtigosSubmetidos();
        artigosPendentes.put(artigo, new ArrayList<>(revisores));
    }

    public void registrarAvaliacaoConcluida(Artigo artigo) {
        if (artigosPendentes.remove(artigo) != null) {
            this.artigosAvaliados++;
        }
    }

    public String imprimirEstatisticas() {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("=== ESTATÍSTICAS DO DASHBOARD DO EVENTO: " + evento.getNome() + "===\n");
        relatorio.append(String.format("Artigos Submetidos: %d\n", artigosSubmetidos));
        relatorio.append(String.format("Artigos Avaliados: %d\n", artigosAvaliados));
        relatorio.append(String.format("Quantidade de Revisores: %d\n", qtdePerfilRevisores));
        relatorio.append(String.format("Quantidade de Artigos Pendentes de Avaliação: %d\n", getQuantidadeArtigosPendentes()));
        relatorio.append("---------------------------------\n");
        relatorio.append("Relação de Artigos Pendentes e Revisores Responsáveis:\n");

        if (artigosPendentes.isEmpty()) {
            relatorio.append("Não há artigos pendentes no momento.\n");
        } else {
            // Percorre o mapa de artigos pendentes
            artigosPendentes.forEach((artigo, revisores) -> {
                relatorio.append(String.format("- Artigo: %s\n", artigo.getTitulo()));
                relatorio.append("  Revisores encarregados:\n");

                for (PerfilRevisor revisor : revisores) {
                    relatorio.append(String.format("    * %s\n", revisor.getPesquisador().getNome()  ));
                }
            });
        }

        relatorio.append("=================================");
        return relatorio.toString();
    }
}