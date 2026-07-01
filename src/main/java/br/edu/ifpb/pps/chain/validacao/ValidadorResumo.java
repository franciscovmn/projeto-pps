package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;

public class ValidadorResumo extends ValidadorBase {

    private static final int MINIMO_CARACTERES = 50;

    @Override
    public void validar(Artigo artigo) {
        String resumo = artigo.getResumo();
        if (resumo == null || resumo.isBlank()) {
            throw new SubmissaoInvalidaException("Resumo do artigo não pode ser vazio.");
        }
        if (resumo.length() < MINIMO_CARACTERES) {
            throw new SubmissaoInvalidaException(
                "Resumo deve ter ao menos " + MINIMO_CARACTERES + " caracteres. Atual: " + resumo.length() + "."
            );
        }
        super.validar(artigo);
    }
}
