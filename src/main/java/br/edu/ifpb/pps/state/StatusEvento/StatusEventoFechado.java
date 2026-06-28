package br.edu.ifpb.pps.state.StatusEvento;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Evento;

public class StatusEventoFechado implements StatusEvento{
    private Evento evento;

    public StatusEventoFechado(Evento evento) {
        this.evento = evento;
    }

    @Override
    public String getNome() {
        return "FECHADO";
    }

    @Override
    public void receberArtigo(Artigo artigo){
        throw new RuntimeException("Artigo não pode ser enviado pois o evento está fechado");
    }
}
