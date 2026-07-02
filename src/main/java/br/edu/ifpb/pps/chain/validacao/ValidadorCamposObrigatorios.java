package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;

public class ValidadorCamposObrigatorios extends ValidadorBase {

    @Override
    public void validar(Artigo artigo) {
        if (artigo.getTitulo() == null || artigo.getTitulo().isBlank()) {
            throw new SubmissaoInvalidaException("Título do artigo não pode ser vazio.");
        }
        if (artigo.getResumo() == null || artigo.getResumo().isBlank()) {
            throw new SubmissaoInvalidaException("Resumo do artigo não pode ser vazio.");
        }
        if (artigo.getNomePDF() == null || artigo.getNomePDF().isBlank()) {
            throw new SubmissaoInvalidaException("Arquivo PDF do artigo não pode ser vazio.");
        }
        if (artigo.getAreasTematicas() == null || artigo.getAreasTematicas().isEmpty()) {
            throw new SubmissaoInvalidaException("O artigo deve ter pelo menos uma área temática.");
        }
        super.validar(artigo);
    }
}
