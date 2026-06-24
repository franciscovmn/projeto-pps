package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.model.Evento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEvento;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class EventoCsvRepository implements CsvRepository<Evento> {

    private final Path caminhoArquivo;

    public EventoCsvRepository(String caminhoArquivo) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
    }

    @Override
    public void salvar(List<Evento> eventos) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("nome;cidade;periodo;categoriaEvento;statusEvento");
            writer.newLine();

            for (Evento e : eventos) {
                writer.write(
                        CsvUtils.escape(e.getNome()) + ";" +
                                CsvUtils.escape(e.getCidade()) + ";" +
                                CsvUtils.escape(e.getPeriodo()) + ";" +
                                e.getCategoriaEvento().name() + ";" +
                                //e.getStatusEvento().name()
                                MapperStatusEvento.paraCsv(e.getStatusEvento())
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
                CategoriaEvento categoria = CategoriaEvento.valueOf(partes[3]);
                //StatusEvento status = StatusEvento.valueOf(partes[4]);

                Evento evento = new Evento(nome, cidade, periodo, categoria);
                //evento.setStatusEvento(status);
                StatusEvento status = MapperStatusEvento.deCsv(partes[4], evento);
                evento.setStatusEvento(status);

                eventos.add(evento);
            }
        }

        return eventos;
    }
}