package br.edu.ifpb.pps.service;

import br.edu.ifpb.pps.chain.validacao.ValidadorSubmissao;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;
import br.edu.ifpb.pps.observer.NotificadorRevisor;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigoRevisao;
import br.edu.ifpb.pps.strategy.distribuicao.EstrategiaDistribuicao;

import java.util.List;
import java.util.stream.Collectors;

public class DistribuicaoService {

    private final ValidadorSubmissao validadorChain;
    private final EstrategiaDistribuicao estrategia;
    private final NotificadorRevisor notificador;

    public DistribuicaoService(ValidadorSubmissao validadorChain,
                               EstrategiaDistribuicao estrategia,
                               NotificadorRevisor notificador) {
        this.validadorChain = validadorChain;
        this.estrategia = estrategia;
        this.notificador = notificador;
    }

    public void distribuir(Artigo artigo, List<PerfilRevisor> candidatos) {
        validadorChain.validar(artigo);

        List<PerfilRevisor> elegiveis = estrategia.distribuir(artigo, candidatos);
        elegiveis = filtrarBlindReview(elegiveis, artigo);

        artigo.setStatusArtigo(new StatusArtigoRevisao(artigo));
        notificador.notificarRevisores(artigo, elegiveis);
    }

    private List<PerfilRevisor> filtrarBlindReview(List<PerfilRevisor> candidatos, Artigo artigo) {
        return candidatos.stream()
                .filter(r -> !artigo.getAutores().contains(r.getPesquisador()))
                .collect(Collectors.toList());
    }
}
