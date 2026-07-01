package br.edu.ifpb.pps.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.pps.enums.Veredito;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Parecer;
import br.edu.ifpb.pps.model.Pesquisador;

public class ParecerCsvRepository implements CsvRepository<Parecer> {

    private final Path caminhoArquivo;
    private final List<Artigo> artigos;
    private final List<Pesquisador> pesquisadores;

    public ParecerCsvRepository(
            String caminhoArquivo,
            List<Artigo> artigos,
            List<Pesquisador> pesquisadores
    ) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
        this.artigos = artigos;
        this.pesquisadores = pesquisadores;
    }

    @Override
    public void salvar(List<Parecer> pareceres) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {

            writer.write(
                    "id;artigoId;revisorEmail;contribuicoes;criticas;veredito;dataConclusao"
            );
            writer.newLine();

            for (Parecer parecer : pareceres) {
                writer.write(
                        CsvUtils.escape(parecer.getId()) + ";" +
                                CsvUtils.escape(parecer.getArtigo().getId()) + ";" +
                                CsvUtils.escape(parecer.getRevisor().getEmail()) + ";" +
                                CsvUtils.escape(parecer.getContribuicoes()) + ";" +
                                CsvUtils.escape(parecer.getCriticas()) + ";" +
                                parecer.getVeredito().name() + ";" +
                                parecer.getDataConclusao()
                );

                writer.newLine();
            }
        }
    }

    @Override
    public List<Parecer> carregar() throws IOException {
        List<Parecer> pareceres = new ArrayList<>();

        if (!Files.exists(caminhoArquivo))
            return pareceres;

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {

            reader.readLine(); // cabeçalho

            String linha;

            while ((linha = reader.readLine()) != null) {

                if (linha.isBlank())
                    continue;

                String[] partes = linha.split(";", -1);

                String id = CsvUtils.unescape(partes[0]);
                String artigoId = CsvUtils.unescape(partes[1]);
                String emailRevisor = CsvUtils.unescape(partes[2]);
                String contribuicoes = CsvUtils.unescape(partes[3]);
                String criticas = CsvUtils.unescape(partes[4]);

                Veredito veredito =
                        Veredito.valueOf(partes[5]);

                LocalDateTime data =
                        LocalDateTime.parse(partes[6]);

                Artigo artigo = buscarArtigo(artigoId);
                Pesquisador revisor = buscarPesquisador(emailRevisor);

                Parecer parecer = new Parecer(
                        id,
                        artigo,
                        revisor,
                        contribuicoes,
                        criticas,
                        veredito,
                        data
                );

                pareceres.add(parecer);
            }
        }

        return pareceres;
    }

    private Artigo buscarArtigo(String id) {
        Optional<Artigo> artigo = artigos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        return artigo.orElse(null);
    }

    private Pesquisador buscarPesquisador(String email) {
        Optional<Pesquisador> pesquisador = pesquisadores.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();

        return pesquisador.orElse(null);
    }
}