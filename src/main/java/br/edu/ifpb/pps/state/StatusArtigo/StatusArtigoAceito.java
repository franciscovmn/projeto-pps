package br.edu.ifpb.pps.state.StatusArtigo;

import br.edu.ifpb.pps.model.Artigo;

public class StatusArtigoAceito implements StatusArtigo {
    private Artigo artigo;

    public StatusArtigoAceito(Artigo artigo){
        this.artigo = artigo;
    }

    @Override
    public String getNome() {
        return "ACEITO";
    }

    @Override
    public String getParecer() {
        return "TO-DO";
    }
}
