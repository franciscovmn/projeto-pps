package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.AreaTematica;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class AreaTematicaCsvRepository implements CsvRepository<AreaTematica> {

    private final Path caminhoArquivo;

    public AreaTematicaCsvRepository(String caminhoArquivo) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
    }

    @Override
    public void salvar(List<AreaTematica> areas) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("descricao");
            writer.newLine();

            for (AreaTematica area : areas) {
                writer.write(CsvUtils.escape(area.getDescricao()));
                writer.newLine();
            }
        }
    }

    @Override
    public List<AreaTematica> carregar() throws IOException {
        List<AreaTematica> areas = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return areas;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                areas.add(new AreaTematica(CsvUtils.unescape(linha)));
            }
        }

        return areas;
    }
}