package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;
import br.edu.ifpb.pps.model.Pesquisador;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerfilRevisorCsvRepository implements CsvRepository<PerfilRevisor> {

    private final Path caminhoArquivo;
    private final List<Pesquisador> pesquisadores;
    private final List<Artigo> artigos;

    public PerfilRevisorCsvRepository(String caminhoArquivo,
                                      List<Pesquisador> pesquisadores,
                                      List<Artigo> artigos) {
        this.caminhoArquivo = Paths.get(caminhoArquivo);
        this.pesquisadores = pesquisadores;
        this.artigos = artigos;
    }

    @Override
    public void salvar(List<PerfilRevisor> perfis) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
            writer.write("pesquisadorEmail;afinidades;artigosRevisadosIds");
            writer.newLine();

            for (PerfilRevisor perfil : perfis) {
                String afinidades = CsvUtils.joinList(
                        perfil.getAfinidades().stream()
                                .map(AreaTematica::getDescricao)
                                .toList()
                );

                String artigosRevisados = perfil.getArtigosRevisados() == null
                        ? ""
                        : CsvUtils.joinList(
                        perfil.getArtigosRevisados().stream()
                                .map(Artigo::getId)
                                .toList()
                );

                writer.write(
                        CsvUtils.escape(perfil.getPesquisador().getEmail()) + ";" +
                                afinidades + ";" +
                                artigosRevisados
                );
                writer.newLine();
            }
        }
    }

    @Override
    public List<PerfilRevisor> carregar() throws IOException {
        List<PerfilRevisor> perfis = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return perfis;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)) {
            String linha = reader.readLine(); // cabeçalho

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";", -1);

                String emailPesquisador = CsvUtils.unescape(partes[0]);
                List<String> afinidadesStr = CsvUtils.splitList(partes[1]);
                List<String> artigosIds = CsvUtils.splitList(partes[2]);

                Pesquisador pesquisador = buscarPesquisadorPorEmail(emailPesquisador);
                if (pesquisador == null) continue;

                PerfilRevisor perfil = new PerfilRevisor(pesquisador);

                for (String afinidade : afinidadesStr) {
                    perfil.getAfinidades().add(new AreaTematica(afinidade));
                }

                List<Artigo> artigosRevisados = new ArrayList<>();
                for (String id : artigosIds) {
                    Artigo artigo = buscarArtigoPorId(id);
                    if (artigo != null) {
                        artigosRevisados.add(artigo);
                    }
                }

                perfil.setArtigosRevisados(artigosRevisados);
                perfis.add(perfil);
            }
        }

        return perfis;
    }

    private Pesquisador buscarPesquisadorPorEmail(String email) {
        Optional<Pesquisador> pesquisador = pesquisadores.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();

        return pesquisador.orElse(null);
    }

    private Artigo buscarArtigoPorId(String id) {
        Optional<Artigo> artigo = artigos.stream()
                .filter(a -> a.getId().equalsIgnoreCase(id))
                .findFirst();

        return artigo.orElse(null);
    }
}