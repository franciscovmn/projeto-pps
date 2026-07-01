package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.*;
import java.io.IOException;
import java.util.List;

public class TesteCsvLeitura {

    public static void main(String[] args) {
        try {
            String diretorioTarget = "src/main/resources/data/";

            // Instanciação dos repositórios base para leitura
            AreaTematicaCsvRepository areaRepo = new AreaTematicaCsvRepository(diretorioTarget + "areas.csv");
            PesquisadorCsvRepository pesquisadorRepo = new PesquisadorCsvRepository(diretorioTarget + "pesquisadores.csv");
            //EventoCsvRepository eventoRepo = new EventoCsvRepository(diretorioTarget + "eventos.csv");

            // =========================
            // 1. CARREGAR DOS CSVs
            // =========================
            List<AreaTematica> areasCarregadas = areaRepo.carregar();
            List<Pesquisador> pesquisadoresCarregados = pesquisadorRepo.carregar();
            //List<Evento> eventosCarregados = eventoRepo.carregar();

            // Repositórios dependentes das listas base carregadas
            ArtigoCsvRepository artigoRepoLeitura =
                    new ArtigoCsvRepository(diretorioTarget + "artigos.csv", pesquisadoresCarregados);
            List<Artigo> artigosCarregados = artigoRepoLeitura.carregar();

            ParecerCsvRepository parecerRepoLeitura = new ParecerCsvRepository(diretorioTarget + "pareceres.csv", artigosCarregados, pesquisadoresCarregados);
            List<Parecer> pareceresCarregados = parecerRepoLeitura.carregar();

            EventoCsvRepository eventoRepo = new EventoCsvRepository(diretorioTarget + "eventos.csv", artigosCarregados);
            List<Evento> eventosCarregados = eventoRepo.carregar();

            PerfilRevisorCsvRepository perfilRepoLeitura =
                    new PerfilRevisorCsvRepository(
                            diretorioTarget + "perfis_revisor.csv",
                            pesquisadoresCarregados,
                            artigosCarregados
                    );
            List<PerfilRevisor> perfisCarregados = perfilRepoLeitura.carregar();

            // =========================
            // 2. EXIBIR DADOS CARREGADOS
            // =========================
            System.out.println("===== AREAS TEMATICAS =====");
            for (AreaTematica area : areasCarregadas) {
                System.out.println("Descricao: " + area.getDescricao());
            }

            System.out.println("\n===== PESQUISADORES =====");
            for (Pesquisador p : pesquisadoresCarregados) {
                System.out.println("Nome: " + p.getNome());
                System.out.println("Email: " + p.getEmail());
                System.out.println("Instituicao: " + p.getInstituicao());
                System.out.println("Papeis: " + p.getPapeisUsuario());
                System.out.print("Afinidades: ");
                for (AreaTematica area : p.getAfinidades()) {
                    System.out.print(area.getDescricao() + " | ");
                }
                System.out.println("\n---");
            }

            System.out.println("\n===== EVENTOS =====");
            for (Evento e : eventosCarregados) {
                System.out.println("Nome: " + e.getNome());
                System.out.println("Cidade: " + e.getCidade());
                System.out.println("Periodo: " + e.getPeriodo());
                System.out.println("data limite submissao: " +e.getDataLimiteSubmissao());
                System.out.println("Categoria: " + e.getCategoriaEvento());
                System.out.println("Status: " + e.getStatusEvento());
                System.out.println("---");
            }

            System.out.println("\n===== ARTIGOS =====");
            for (Artigo a : artigosCarregados) {
                System.out.println("ID: " + a.getId());
                System.out.println("Titulo: " + a.getTitulo());
                System.out.println("Autor principal: " + a.getPesquisador().getNome());

                System.out.print("Coautores: ");
                for (Pesquisador coautor : a.getCoAutores()) {
                    System.out.print(coautor.getNome() + " | ");
                }
                System.out.println();

                System.out.print("Areas: ");
                for (AreaTematica area : a.getAreasTematicas()) {
                    System.out.print(area.getDescricao() + " | ");
                }
                System.out.println();

                System.out.println("Status: " + a.getStatusArtigo());
                System.out.println("---");
            }

            System.out.println("\n===== PARECERES =====");
            for (Parecer parecer : pareceresCarregados) {
                System.out.println("ID: " + parecer.getId());
                System.out.println("Artigo: " + (parecer.getArtigo() != null ? parecer.getArtigo().getTitulo() : "null"));
                System.out.println("Revisor: " + (parecer.getRevisor() != null ? parecer.getRevisor().getNome() : "null"));
                System.out.println("Contribuicoes: " + parecer.getContribuicoes());
                System.out.println("Criticas: " + parecer.getCriticas());
                System.out.println("Veredito: " + parecer.getVeredito());
                System.out.println("Data Conclusao: " + parecer.getDataConclusao());

                System.out.println("---");
            }

            System.out.println("\n===== PERFIS DE REVISOR =====");
            for (PerfilRevisor perfil : perfisCarregados) {
                System.out.println("Pesquisador: " + perfil.getPesquisador().getNome());

                System.out.print("Afinidades: ");
                for (AreaTematica area : perfil.getAfinidades()) {
                    System.out.print(area.getDescricao() + " | ");
                }
                System.out.println();

                System.out.print("Artigos revisados: ");
                if (perfil.getArtigosRevisados() != null) {
                    for (Artigo artigo : perfil.getArtigosRevisados()) {
                        System.out.print(artigo.getId() + " - " + artigo.getTitulo() + " | ");
                    }
                }
                System.out.println("\n---");
            }

            System.out.println("\nLeitura concluída com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao carregar os arquivos CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}