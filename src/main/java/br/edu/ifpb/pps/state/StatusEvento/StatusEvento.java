package br.edu.ifpb.pps.state.StatusEvento;

import br.edu.ifpb.pps.model.Artigo;

public interface StatusEvento {
    public String getNome();
    public void receberArtigo(Artigo artigo);
}
