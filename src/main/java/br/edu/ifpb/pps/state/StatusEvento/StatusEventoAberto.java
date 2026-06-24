package br.edu.ifpb.pps.state.StatusEvento;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Evento;

public class StatusEventoAberto implements StatusEvento {
    private Evento evento;

    public StatusEventoAberto(Evento evento) {
        this.evento = evento;
    }

    @Override
    public String getNome() {
        return "ABERTO";
    }

    @Override
    public void receberArtigo(Artigo artigo){
        System.out.println("TO-DO");
    }
}
