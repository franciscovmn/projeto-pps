package br.edu.ifpb.pps.strategy.distribuicao;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

import java.util.List;

public interface EstrategiaDistribuicao {
    List<PerfilRevisor> distribuir(Artigo artigo, List<PerfilRevisor> candidatos);
}
