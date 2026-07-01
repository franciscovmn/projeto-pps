package br.edu.ifpb.pps.strategy.distribuicao;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistribuicaoPorAfinidade implements EstrategiaDistribuicao {

    @Override
    public List<PerfilRevisor> distribuir(Artigo artigo, List<PerfilRevisor> candidatos) {
        Set<String> areasDoArtigo = artigo.getAreasTematicas().stream()
                .map(AreaTematica::getDescricao)
                .collect(Collectors.toSet());

        return candidatos.stream()
                .filter(revisor -> revisor.getAfinidades().stream()
                        .map(AreaTematica::getDescricao)
                        .anyMatch(areasDoArtigo::contains))
                .collect(Collectors.toList());
    }
}
