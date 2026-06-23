package br.edu.ifpb.pps.state.StatusArtigo;

import br.edu.ifpb.pps.model.Artigo;

public class StatusArtigoSubmetido implements StatusArtigo{
    private Artigo artigo;

    public StatusArtigoSubmetido(Artigo artigo){
        this.artigo = artigo;
    }

    @Override
    public String getParecer() {
        return "nenhum parecer disponível";
    }
}
