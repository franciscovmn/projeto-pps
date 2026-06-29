package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigo;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtigoCsvRepository implements CsvRepository<Artigo> {

    private final Path caminhoArquivo;
    private final List<Pesquisador> pesquisadoresCadastrados;

    public ArtigoCsvRepository(String caminhoArquivo, List<Pesquisador> pesquisadoresCadastrados) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
        this.pesquisadoresCadastrados = pesquisadoresCadastrados;
    }

    @Override
    public void salvar(List<Artigo> artigos) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("id;titulo;autorEmail;resumo;nomePDF;coAutoresEmails;areasTematicas;statusArtigo");
            writer.newLine();

            for (Artigo artigo : artigos) {
                String coAutores = CsvUtils.joinList(
                        artigo.getCoAutores().stream()
                                .map(Pesquisador::getEmail)
                                .toList()
                );

                String areas = CsvUtils.joinList(
                        artigo.getAreasTematicas().stream()
                                .map(AreaTematica::getDescricao)
                                .toList()
                );

                writer.write(
                        CsvUtils.escape(artigo.getId()) + ";" +
                                CsvUtils.escape(artigo.getTitulo()) + ";" +
                                CsvUtils.escape(artigo.getPesquisador().getEmail()) + ";" +
                                CsvUtils.escape(artigo.getResumo()) + ";" +
                                CsvUtils.escape(artigo.getNomePDF()) + ";" +
                                coAutores + ";" +
                                areas + ";" +
                                MapperStatusArtigo.paraCsv(artigo.getStatusArtigo())
                );
                writer.newLine();
            }
        }
    }

    @Override
    public List<Artigo> carregar() throws IOException {
        List<Artigo> artigos = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return artigos;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";", -1);

                String id = CsvUtils.unescape(partes[0]);
                String titulo = CsvUtils.unescape(partes[1]);
                String autorEmail = CsvUtils.unescape(partes[2]);
                String resumo = CsvUtils.unescape(partes[3]);
                String nomePdf = CsvUtils.unescape(partes[4]);

                List<String> coAutoresEmails = CsvUtils.splitList(partes[5]);
                List<String> areasStr = CsvUtils.splitList(partes[6]);

                Pesquisador autor = buscarPesquisadorPorEmail(autorEmail);

                List<Pesquisador> coAutores = new ArrayList<>();
                for (String email : coAutoresEmails) {
                    Pesquisador coautor = buscarPesquisadorPorEmail(email);
                    if (coautor != null) {
                        coAutores.add(coautor);
                    }
                }

                List<AreaTematica> areas = new ArrayList<>();
                for (String descricao : areasStr) {
                    areas.add(new AreaTematica(descricao));
                }

                Artigo artigo = new Artigo(id, titulo, autor, resumo, nomePdf, coAutores, areas);
                //artigo.setStatusArtigo(status);
                StatusArtigo status = MapperStatusArtigo.deCsv(partes[7], artigo);
                artigo.setStatusArtigo(status);

                artigos.add(artigo);
            }
        }

        return artigos;
    }

    private Pesquisador buscarPesquisadorPorEmail(String email) {
        Optional<Pesquisador> pesquisador = pesquisadoresCadastrados.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();

        return pesquisador.orElse(null);
    }
}