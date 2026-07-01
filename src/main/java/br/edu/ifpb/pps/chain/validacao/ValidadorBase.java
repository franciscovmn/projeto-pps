package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.model.Artigo;

public abstract class ValidadorBase implements ValidadorSubmissao {

    private ValidadorBase proximo;

    public ValidadorBase setProximo(ValidadorBase proximo) {
        this.proximo = proximo;
        return proximo;
    }

    @Override
    public void validar(Artigo artigo) {
        if (proximo != null) {
            proximo.validar(artigo);
        }
    }
}
