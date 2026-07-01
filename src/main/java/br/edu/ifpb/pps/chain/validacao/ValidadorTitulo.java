package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;

public class ValidadorTitulo extends ValidadorBase {

    @Override
    public void validar(Artigo artigo) {
        if (artigo.getTitulo() == null || artigo.getTitulo().isBlank()) {
            throw new SubmissaoInvalidaException("Título do artigo não pode ser vazio.");
        }
        super.validar(artigo);
    }
}
