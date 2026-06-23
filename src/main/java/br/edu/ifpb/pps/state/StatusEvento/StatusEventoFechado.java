package br.edu.ifpb.pps.state.StatusEvento;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Evento;

public class StatusEventoFechado implements StatusEvento{
    private Evento evento;

    public StatusEventoFechado(Evento evento) {
        this.evento = evento;
    }

    @Override
    public void receberArtigo(Artigo artigo){
        //TO-DO: lógica de enviar para os Revisores
    }
}
