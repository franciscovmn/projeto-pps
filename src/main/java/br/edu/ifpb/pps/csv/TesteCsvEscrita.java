package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.enums.PapelUsuario;
import br.edu.ifpb.pps.enums.Veredito;
import br.edu.ifpb.pps.model.*;
import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigoAceito;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TesteCsvEscrita {

    public static void main(String[] args) {
        try {
            // =========================
            // 1. CRIAR DADOS DE TESTE
            // =========================

            // Áreas temáticas (Adicionadas as áreas 5 e 6)
            AreaTematica area1 = new AreaTematica("Engenharia de Software");
            AreaTematica area2 = new AreaTematica("Inteligencia Artificial");
            AreaTematica area3 = new AreaTematica("Banco de Dados");
            AreaTematica area4 = new AreaTematica("Computacao em Nuvem");
            AreaTematica area5 = new AreaTematica("Sistemas Distribuidos");
            AreaTematica area6 = new AreaTematica("Seguranca da Informacao");

            List<AreaTematica> areas = new ArrayList<>();
            areas.add(area1);
            areas.add(area2);
            areas.add(area3);
            areas.add(area4);
            areas.add(area5);
            areas.add(area6);

            // Pesquisadores
            Pesquisador murilo = new Pesquisador("Murilo Maciel", "murilo@ifpb.edu.br", "123456", "IFPB");
            murilo.getPapeisUsuario().add(PapelUsuario.AUTOR);
            murilo.getPapeisUsuario().add(PapelUsuario.REVISOR);
            murilo.getAfinidades().add(area1);
            murilo.getAfinidades().add(area2);

            Pesquisador ana = new Pesquisador("Ana Souza", "ana@ufpb.br", "abc123", "UFPB");
            ana.getPapeisUsuario().add(PapelUsuario.AUTOR);
            ana.getAfinidades().add(area3);

            Pesquisador carlos = new Pesquisador("Carlos Lima", "carlos@ifpe.edu.br", "senha123", "IFPE");
            carlos.getPapeisUsuario().add(PapelUsuario.REVISOR);
            carlos.getAfinidades().add(area4);
            carlos.getAfinidades().add(area1);

            Pesquisador francisco = new Pesquisador("Francisco Viana", "francisco@ifpb.edu.br", "coord123", "IFPB");
            francisco.getPapeisUsuario().add(PapelUsuario.AUTOR);
            francisco.getPapeisUsuario().add(PapelUsuario.COORDENADOR);
            francisco.getAfinidades().add(area1);
            francisco.getAfinidades().add(area3);

            Pesquisador beatriz = new Pesquisador("Beatriz Costa", "beatriz@ufcg.edu.br", "b12345", "UFCG");
            beatriz.getPapeisUsuario().add(PapelUsuario.AUTOR);
            beatriz.getPapeisUsuario().add(PapelUsuario.REVISOR);
            beatriz.getAfinidades().add(area5);
            beatriz.getAfinidades().add(area6);

            Pesquisador ricardo = new Pesquisador("Ricardo Santos", "ricardo@ifpb.edu.br", "ric987", "IFPB");
            ricardo.getPapeisUsuario().add(PapelUsuario.REVISOR);
            ricardo.getAfinidades().add(area2);
            ricardo.getAfinidades().add(area5);

            List<Pesquisador> pesquisadores = new ArrayList<>();
            pesquisadores.add(murilo);
            pesquisadores.add(ana);
            pesquisadores.add(carlos);
            pesquisadores.add(francisco);
            pesquisadores.add(beatriz);
            pesquisadores.add(ricardo);

            // Eventos
            Evento evento1 = new Evento(
                    "SBES 2026",
                    "João Pessoa",
                    "20 a 24 de Setembro de 2026",
                    LocalDateTime.of(2026, 8, 10, 23, 59),
                    CategoriaEvento.FULL_PAPER
            );

            List<Evento> eventos = new ArrayList<>();
            eventos.add(evento1);

            // Artigos (Adicionados os artigos A3 e A4)
            List<AreaTematica> areasArtigo1 = new ArrayList<>();
            areasArtigo1.add(area1);
            areasArtigo1.add(area2);

            List<Pesquisador> coautoresArtigo1 = new ArrayList<>();
            coautoresArtigo1.add(ana);

            Artigo artigo1 = new Artigo(
                    "A1",
                    "Uso de Padrões em Sistemas de Submissão",
                    murilo,
                    "Resumo do artigo...",
                    "artigoA1.pdf",
                    coautoresArtigo1,
                    areasArtigo1
            );

            List<AreaTematica> areasArtigo2 = new ArrayList<>();
            areasArtigo2.add(area4);
            areasArtigo2.add(area3);

            List<Pesquisador> coautoresArtigo2 = new ArrayList<>();
            coautoresArtigo2.add(murilo);

            Artigo artigo2 = new Artigo(
                    "A2",
                    "Persistência CSV em Java",
                    carlos,
                    "Resumo do segundo artigo...",
                    "artigoA2.pdf",
                    coautoresArtigo2,
                    areasArtigo2
            );

            List<AreaTematica> areasArtigo3 = new ArrayList<>();
            areasArtigo3.add(area5);
            areasArtigo3.add(area6);

            List<Pesquisador> coautoresArtigo3 = new ArrayList<>();
            coautoresArtigo3.add(ana);

            Artigo artigo3 = new Artigo(
                    "A3",
                    "Arquitetura de Microsserviços Resilientes",
                    beatriz,
                    "Resumo do terceiro artigo...",
                    "artigoA3.pdf",
                    coautoresArtigo3,
                    areasArtigo3
            );

            List<AreaTematica> areasArtigo4 = new ArrayList<>();
            areasArtigo4.add(area2);
            areasArtigo4.add(area1);

            List<Pesquisador> coautoresArtigo4 = new ArrayList<>();
            coautoresArtigo4.add(murilo);

            Artigo artigo4 = new Artigo(
                    "A4",
                    "Análise de Sentimentos em Revisões de Código",
                    francisco,
                    "Resumo do quarto artigo...",
                    "artigoA4.pdf",
                    coautoresArtigo4,
                    areasArtigo4
            );

            // Pareceres (Adicionados os pareceres P3 e P4)
            Parecer parecer1 = new Parecer(
                    "P1",
                    artigo1,
                    carlos,
                    "Excelente artigo.",
                    "Pequenos ajustes de escrita.",
                    Veredito.ACEITO,
                    LocalDateTime.now()
            );

            Parecer parecer2 = new Parecer(
                    "P2",
                    artigo2,
                    murilo,
                    "Tema interessante.",
                    "Faltam experimentos.",
                    Veredito.ACEITO,
                    LocalDateTime.now()
            );

            Parecer parecer3 = new Parecer(
                    "P3",
                    artigo3,
                    ricardo,
                    "Trabalho muito bem estruturado.",
                    "Sugerido expandir a fundamentação.",
                    Veredito.ACEITO,
                    LocalDateTime.now()
            );

            Parecer parecer4 = new Parecer(
                    "P4",
                    artigo4,
                    beatriz,
                    "Contribuição relevante para a área.",
                    "Revisar as referências.",
                    Veredito.ACEITO,
                    LocalDateTime.now()
            );

            // Definindo status aceito para todos os novos artigos
            artigo1.setStatusArtigo(new StatusArtigoAceito(artigo1));
            artigo2.setStatusArtigo(new StatusArtigoAceito(artigo2));
            artigo3.setStatusArtigo(new StatusArtigoAceito(artigo3));
            artigo4.setStatusArtigo(new StatusArtigoAceito(artigo4));

            List<Parecer> pareceres = new ArrayList<>();
            pareceres.add(parecer1);
            pareceres.add(parecer2);
            pareceres.add(parecer3);
            pareceres.add(parecer4);

            List<Artigo> artigos = new ArrayList<>();
            artigos.add(artigo1);
            artigos.add(artigo2);
            artigos.add(artigo3);
            artigos.add(artigo4);

            // Salvando todos os 4 artigos
            evento1.setArtigos(artigos);

            // Perfis de revisor
            PerfilRevisor perfilMurilo = new PerfilRevisor(murilo);
            perfilMurilo.getAfinidades().add(area1);
            perfilMurilo.getAfinidades().add(area2);
            perfilMurilo.setArtigosRevisados(new ArrayList<>());
            perfilMurilo.getArtigosRevisados().add(artigo2);

            PerfilRevisor perfilCarlos = new PerfilRevisor(carlos);
            perfilCarlos.getAfinidades().add(area4);
            perfilCarlos.getAfinidades().add(area1);
            perfilCarlos.setArtigosRevisados(new ArrayList<>());
            perfilCarlos.getArtigosRevisados().add(artigo1);

            PerfilRevisor perfilBeatriz = new PerfilRevisor(beatriz);
            perfilBeatriz.getAfinidades().add(area5);
            perfilBeatriz.getAfinidades().add(area6);
            perfilBeatriz.setArtigosRevisados(new ArrayList<>());
            perfilBeatriz.getArtigosRevisados().add(artigo4);

            PerfilRevisor perfilRicardo = new PerfilRevisor(ricardo);
            perfilRicardo.getAfinidades().add(area2);
            perfilRicardo.getAfinidades().add(area5);
            perfilRicardo.setArtigosRevisados(new ArrayList<>());
            perfilRicardo.getArtigosRevisados().add(artigo3);

            List<PerfilRevisor> perfis = new ArrayList<>();
            perfis.add(perfilMurilo);
            perfis.add(perfilCarlos);
            perfis.add(perfilBeatriz);
            perfis.add(perfilRicardo);

            // =========================
            // 2. SALVAR NOS CSVs
            // =========================
            String diretorioTarget = "src/main/resources/data/";

            File pasta = new File(diretorioTarget);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            AreaTematicaCsvRepository areaRepo = new AreaTematicaCsvRepository(diretorioTarget + "areas.csv");
            PesquisadorCsvRepository pesquisadorRepo = new PesquisadorCsvRepository(diretorioTarget + "pesquisadores.csv");
            ArtigoCsvRepository artigoRepo = new ArtigoCsvRepository(diretorioTarget + "artigos.csv", pesquisadores);
            ParecerCsvRepository parecerRepo = new ParecerCsvRepository(
                    diretorioTarget + "pareceres.csv",
                    artigos,
                    pesquisadores
            );
            EventoCsvRepository eventoRepo = new EventoCsvRepository(diretorioTarget + "eventos.csv", artigos);
            PerfilRevisorCsvRepository perfilRepo =
                    new PerfilRevisorCsvRepository(diretorioTarget + "perfis_revisor.csv", pesquisadores, artigos);

            areaRepo.salvar(areas);
            pesquisadorRepo.salvar(pesquisadores);
            eventoRepo.salvar(eventos);
            artigoRepo.salvar(artigos);
            parecerRepo.salvar(pareceres);
            perfilRepo.salvar(perfis);

            System.out.println("Arquivos CSV gerados com sucesso em: " + diretorioTarget);

        } catch (IOException e) {
            System.err.println("Erro ao salvar os arquivos CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}