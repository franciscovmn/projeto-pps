package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.enums.PapelUsuario;
import br.edu.ifpb.pps.model.*;
import br.edu.ifpb.pps.enums.CategoriaEvento;

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

            // Áreas temáticas
            AreaTematica area1 = new AreaTematica("Engenharia de Software");
            AreaTematica area2 = new AreaTematica("Inteligencia Artificial");
            AreaTematica area3 = new AreaTematica("Banco de Dados");
            AreaTematica area4 = new AreaTematica("Computacao em Nuvem");

            List<AreaTematica> areas = new ArrayList<>();
            areas.add(area1);
            areas.add(area2);
            areas.add(area3);
            areas.add(area4);

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

            List<Pesquisador> pesquisadores = new ArrayList<>();
            pesquisadores.add(murilo);
            pesquisadores.add(ana);
            pesquisadores.add(carlos);
            pesquisadores.add(francisco);

            // Eventos
            Evento evento1 = new Evento(
                    "SBES 2026",
                    "João Pessoa",
                    "20 a 24 de Setembro de 2026",
                    LocalDateTime.of(2026, 8, 10, 23, 59),
                    CategoriaEvento.FULL_PAPER
            );

            Evento evento2 = new Evento(
                    "WTD 2026",
                    "Recife",
                    "15 a 17 de Outubro de 2026",
                    LocalDateTime.of(2026, 9, 5, 23, 59),
                    CategoriaEvento.SHORT_PAPER
            );

            List<Evento> eventos = new ArrayList<>();
            eventos.add(evento1);
            eventos.add(evento2);

            // Artigos
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

            List<Artigo> artigos = new ArrayList<>();
            artigos.add(artigo1);
            artigos.add(artigo2);

            // salvando os artigos nos dois eventos
            evento1.setArtigos(artigos);
            evento2.setArtigos(artigos);

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

            List<PerfilRevisor> perfis = new ArrayList<>();
            perfis.add(perfilMurilo);
            perfis.add(perfilCarlos);

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
            //EventoCsvRepository eventoRepo = new EventoCsvRepository(diretorioTarget + "eventos.csv");
            ArtigoCsvRepository artigoRepo = new ArtigoCsvRepository(diretorioTarget + "artigos.csv", pesquisadores);
            EventoCsvRepository eventoRepo = new EventoCsvRepository(diretorioTarget + "eventos.csv", artigos);
            PerfilRevisorCsvRepository perfilRepo =
                    new PerfilRevisorCsvRepository(diretorioTarget + "perfis_revisor.csv", pesquisadores, artigos);

            areaRepo.salvar(areas);
            pesquisadorRepo.salvar(pesquisadores);
            eventoRepo.salvar(eventos);
            artigoRepo.salvar(artigos);
            perfilRepo.salvar(perfis);

            System.out.println("Arquivos CSV gerados com sucesso em: " + diretorioTarget);

        } catch (IOException e) {
            System.err.println("Erro ao salvar os arquivos CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}