package br.edu.ifpb.pps.modulos;

import br.edu.ifpb.pps.dashboard.Dashboard;
import br.edu.ifpb.pps.enums.Veredito;
import br.edu.ifpb.pps.mediator.ModuloSistema;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Parecer;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigoAceito;
import br.edu.ifpb.pps.state.StatusArtigo.StatusArtigoRejeitado;

public class ModuloRevisao extends ModuloSistema {

    public void registrarParecer(Artigo artigo, Parecer parecer) {
        artigo.adicionarParecer(parecer);
    }

    public void agregarResultado(Artigo artigo) {
        long positivos = artigo.getPareceres().stream()
                .filter(p -> p.getVeredito() == Veredito.ACEITO
                        || p.getVeredito() == Veredito.FRACAMENTE_ACEITO)
                .count();
        long negativos = artigo.getPareceres().size() - positivos;

        if (positivos > negativos) {
            artigo.setStatusArtigo(new StatusArtigoAceito(artigo));
        } else {
            artigo.setStatusArtigo(new StatusArtigoRejeitado(artigo));
        }

        Dashboard.getInstance().registrarAvaliacaoConcluida(artigo);
    }
}
