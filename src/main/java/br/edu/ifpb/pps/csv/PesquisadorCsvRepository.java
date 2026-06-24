package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.enums.PapelUsuario;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Pesquisador;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class PesquisadorCsvRepository implements CsvRepository<Pesquisador> {

    private final Path caminhoArquivo;

    public PesquisadorCsvRepository(String caminhoArquivo) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
    }

    @Override
    public void salvar(List<Pesquisador> pesquisadores) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("nome;email;senha;instituicao;papeisUsuario;afinidades");
            writer.newLine();

            for (Pesquisador p : pesquisadores) {
                String papeis = CsvUtils.joinList(
                        p.getPapeisUsuario().stream()
                                .map(Enum::name)
                                .toList()
                );

                String afinidades = CsvUtils.joinList(
                        p.getAfinidades().stream()
                                .map(AreaTematica::getDescricao)
                                .toList()
                );

                writer.write(
                        CsvUtils.escape(p.getNome()) + ";" +
                                CsvUtils.escape(p.getEmail()) + ";" +
                                CsvUtils.escape(p.getSenha()) + ";" +
                                CsvUtils.escape(p.getInstituicao()) + ";" +
                                papeis + ";" +
                                afinidades
                );
                writer.newLine();
            }
        }
    }

    @Override
    public List<Pesquisador> carregar() throws IOException {
        List<Pesquisador> pesquisadores = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return pesquisadores;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";", -1);

                String nome = CsvUtils.unescape(partes[0]);
                String email = CsvUtils.unescape(partes[1]);
                String senha = CsvUtils.unescape(partes[2]);
                String instituicao = CsvUtils.unescape(partes[3]);

                Pesquisador pesquisador = new Pesquisador(nome, email, senha, instituicao);

                // papeis
                List<String> papeisStr = CsvUtils.splitList(partes[4]);
                for (String papel : papeisStr) {
                    pesquisador.getPapeisUsuario().add(PapelUsuario.valueOf(papel));
                }

                // afinidades
                List<String> afinidadesStr = CsvUtils.splitList(partes[5]);
                for (String descricao : afinidadesStr) {
                    pesquisador.getAfinidades().add(new AreaTematica(descricao));
                }

                pesquisadores.add(pesquisador);
            }
        }

        return pesquisadores;
    }
}