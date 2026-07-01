package br.edu.ifpb.pps.observer;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

public interface ObservadorRevisor {
    void atualizar(Artigo artigo, PerfilRevisor revisor);
}
