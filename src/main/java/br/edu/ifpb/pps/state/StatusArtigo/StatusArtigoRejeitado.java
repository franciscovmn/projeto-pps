package br.edu.ifpb.pps.state.StatusArtigo;

import br.edu.ifpb.pps.model.Artigo;

public class StatusArtigoRejeitado implements StatusArtigo{
    private Artigo artigo;

    public StatusArtigoRejeitado(Artigo artigo){
        this.artigo = artigo;
    }

    @Override
    public String getParecer() {
        return "TO-DO";
    }
}
