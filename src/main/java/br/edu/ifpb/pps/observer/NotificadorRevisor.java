package br.edu.ifpb.pps.observer;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.PerfilRevisor;

public class NotificadorRevisor {

    private final List<ObservadorRevisor> observadores = new ArrayList<>();

    public void registrar(ObservadorRevisor observador) {
        observadores.add(observador);
    }

    public void remover(ObservadorRevisor observador) {
        observadores.remove(observador);
    }

    private void notificar(Artigo artigo, PerfilRevisor revisor) {
        for (ObservadorRevisor observador : observadores) {
            observador.atualizar(artigo, revisor);
        }
    }

    public void notificarRevisores(Artigo artigo, List<PerfilRevisor> revisores) {
        for (PerfilRevisor revisor : revisores) {
            notificar(artigo, revisor);
        }
    }
}
