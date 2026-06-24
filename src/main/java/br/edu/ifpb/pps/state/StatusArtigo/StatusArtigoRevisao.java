package br.edu.ifpb.pps.state.StatusArtigo;

import br.edu.ifpb.pps.model.Artigo;

public class StatusArtigoRevisao implements StatusArtigo {
    private Artigo artigo;

    public StatusArtigoRevisao(Artigo artigo){
        this.artigo = artigo;
    }

    @Override
    public String getNome() {
        return "EM_REVISAO";
    }

    @Override
    public String getParecer() {
        return "nenhum parecer disponível";
    }
}
