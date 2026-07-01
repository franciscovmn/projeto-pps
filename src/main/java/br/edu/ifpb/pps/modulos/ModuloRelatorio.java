package br.edu.ifpb.pps.modulos;

import br.edu.ifpb.pps.relatorios.GeradorRelatorio;
import br.edu.ifpb.pps.relatorios.RelatorioCsv;
import br.edu.ifpb.pps.relatorios.RelatorioHtml;
import br.edu.ifpb.pps.relatorios.RelatorioTexto;
import lombok.Getter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ModuloRelatorio {
    private final Map<String, GeradorRelatorio> formatosDisponiveis = new HashMap<>();
    private final String diretorioTarget = "src/main/resources/relatorios/";
    private String ultimoArquivoGerado;

    public ModuloRelatorio() {
        registrarFormato("txt", new RelatorioTexto());
        registrarFormato("html", new RelatorioHtml());
        registrarFormato("csv", new RelatorioCsv());
    }

    public void registrarFormato(String tipo, GeradorRelatorio gerador) {
        this.formatosDisponiveis.put(tipo.toUpperCase(), gerador);
    }

    public void gerarRelatorio(String tipo, String nomeBaseArquivo) throws IOException {
        GeradorRelatorio gerador = formatosDisponiveis.get(tipo.toUpperCase());

        if (gerador == null) {
            throw new IllegalArgumentException("Formato de relatório não suportado: " + tipo);
        }

        String caminhoArquivo = diretorioTarget + nomeBaseArquivo + "." + tipo;
        gerador.gerarRelatorio(caminhoArquivo);
        this.ultimoArquivoGerado = caminhoArquivo;
    }

    public void gerarTodosOsRelatorios(String nomeBaseArquivo) throws IOException {
        for (String tipo : formatosDisponiveis.keySet()) {
            gerarRelatorio(tipo.toLowerCase(), nomeBaseArquivo);
        }
    }
}