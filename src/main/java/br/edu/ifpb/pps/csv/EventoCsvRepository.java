package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Evento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEvento;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventoCsvRepository implements CsvRepository<Evento> {

    private final Path caminhoArquivo;
    private List<Artigo> artigosCadastrados;

    public EventoCsvRepository(String caminhoArquivo, List<Artigo> artigosCadastrados) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
        this.artigosCadastrados = artigosCadastrados;
    }

    public EventoCsvRepository(String caminhoArquivo) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
    }

    @Override
    public void salvar(List<Evento> eventos) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("nome;cidade;periodo;dataLimiteSubmissao;categoriaEvento;statusEvento;artigos");
            writer.newLine();

            for (Evento e : eventos) {
                String artigos = CsvUtils.joinList(
                        e.getArtigos()
                                .stream()
                                .map(Artigo::getId)
                                .toList()
                );

                writer.write(
                        CsvUtils.escape(e.getNome()) + ";" +
                                CsvUtils.escape(e.getCidade()) + ";" +
                                CsvUtils.escape(e.getPeriodo()) + ";" +
                                CsvUtils.escape(e.getDataLimiteSubmissao().toString()) + ";" +
                                e.getCategoriaEvento().name() + ";" +
                                MapperStatusEvento.paraCsv(e.getStatusEvento()) + ";" +
                                artigos
                );
                writer.newLine();
            }
        }
    }

    @Override
    public List<Evento> carregar() throws IOException {
        List<Evento> eventos = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return eventos;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";", -1);

                String nome = CsvUtils.unescape(partes[0]);
                String cidade = CsvUtils.unescape(partes[1]);
                String periodo = CsvUtils.unescape(partes[2]);
                LocalDateTime dataLimite = LocalDateTime.parse(CsvUtils.unescape(partes[3]));
                CategoriaEvento categoria = CategoriaEvento.valueOf(partes[4]);
                List<String> idsArtigos = CsvUtils.splitList(partes[6]);

                Evento evento = new Evento(nome, cidade, periodo, dataLimite, categoria);
                //evento.setStatusEvento(status);

                List<Artigo> artigos = new ArrayList<>();

                for (String id : idsArtigos) {
                    Artigo artigo = buscarArtigo(id);
                    if (artigo != null) {
                        artigos.add(artigo);
                    }
                }

                evento.setArtigos(artigos);

                StatusEvento status = MapperStatusEvento.deCsv(partes[5], evento);
                evento.setStatusEvento(status);

                eventos.add(evento);
            }
        }

        return eventos;
    }

    private Artigo buscarArtigo(String id) {
        Optional<Artigo> artigo =
                artigosCadastrados.stream()
                        .filter(a -> a.getId().equals(id))
                        .findFirst();

        return artigo.orElse(null);
    }
}