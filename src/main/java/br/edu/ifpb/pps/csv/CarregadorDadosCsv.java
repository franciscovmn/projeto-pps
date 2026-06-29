package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.*;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

@Getter
public class CarregadorDadosCsv {
    private static CarregadorDadosCsv instancia;

    private List<AreaTematica> areas;
    private List<Pesquisador> pesquisadores;
    private List<Artigo> artigos;
    private List<Evento> eventos;
    private List<PerfilRevisor> perfis;

    private CarregadorDadosCsv() throws IOException {
        String diretorioTarget = "src/main/resources/data/";

        AreaTematicaCsvRepository areaRepo =
                new AreaTematicaCsvRepository(diretorioTarget + "areas.csv");

        PesquisadorCsvRepository pesquisadorRepo =
                new PesquisadorCsvRepository(diretorioTarget + "pesquisadores.csv");

        this.areas = areaRepo.carregar();
        this.pesquisadores = pesquisadorRepo.carregar();

        ArtigoCsvRepository artigoRepo =
                new ArtigoCsvRepository(
                        diretorioTarget + "artigos.csv",
                        pesquisadores);

        this.artigos = artigoRepo.carregar();

        // Carrega eventos
        EventoCsvRepository eventoRepo =
                new EventoCsvRepository(
                        diretorioTarget + "eventos.csv",
                        artigos);

        this.eventos = eventoRepo.carregar();

        PerfilRevisorCsvRepository perfilRepo =
                new PerfilRevisorCsvRepository(
                        diretorioTarget + "perfis_revisor.csv",
                        pesquisadores,
                        artigos);

        this.perfis = perfilRepo.carregar();
    }

    public static CarregadorDadosCsv getInstance() throws IOException {
        if (instancia == null) {
            instancia = new CarregadorDadosCsv();
        }
        return instancia;
    }
}