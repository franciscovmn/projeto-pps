package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.*;

import java.util.List;

public class ExemploOrdemCarregamentoArquivos {
    public static void main(String[] args) {
        try{
            AreaTematicaCsvRepository areaRepo = new AreaTematicaCsvRepository("areas.csv");
            List<AreaTematica> areas = areaRepo.carregar();

            PesquisadorCsvRepository pesquisadorRepo = new PesquisadorCsvRepository("pesquisadores.csv");
            List<Pesquisador> pesquisadores = pesquisadorRepo.carregar();

            EventoCsvRepository eventoRepo = new EventoCsvRepository("eventos.csv");
            List<Evento> eventos = eventoRepo.carregar();

            ArtigoCsvRepository artigoRepo = new ArtigoCsvRepository("artigos.csv", pesquisadores);
            List<Artigo> artigos = artigoRepo.carregar();

            PerfilRevisorCsvRepository perfilRepo =
                    new PerfilRevisorCsvRepository("perfis.csv", pesquisadores, artigos);
            List<PerfilRevisor> perfis = perfilRepo.carregar();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
