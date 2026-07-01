package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;

public class ValidadorAreaTematica extends ValidadorBase {

    @Override
    public void validar(Artigo artigo) {
        if (artigo.getAreasTematicas() == null || artigo.getAreasTematicas().isEmpty()) {
            throw new SubmissaoInvalidaException("Artigo deve ter ao menos uma área temática.");
        }
        super.validar(artigo);
    }
}
