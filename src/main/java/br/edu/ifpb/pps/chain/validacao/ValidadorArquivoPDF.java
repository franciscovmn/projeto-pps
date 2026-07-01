package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;

public class ValidadorArquivoPDF extends ValidadorBase {

    @Override
    public void validar(Artigo artigo) {
        if (artigo.getNomePDF() == null || artigo.getNomePDF().isBlank()) {
            throw new SubmissaoInvalidaException("Artigo deve ter um arquivo PDF associado.");
        }
        super.validar(artigo);
    }
}
