package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.modulos.ModuloEvento;

import java.time.LocalDateTime;

public class ValidadorPrazo extends ValidadorBase {

    private final ModuloEvento moduloEvento;

    public ValidadorPrazo(ModuloEvento moduloEvento) {
        this.moduloEvento = moduloEvento;
    }

    @Override
    public void validar(Artigo artigo) {
        LocalDateTime prazo = moduloEvento.getEventoAtual().getDataLimiteSubmissao();
        if (!prazo.isAfter(LocalDateTime.now())) {
            throw new SubmissaoInvalidaException("Prazo de submissão encerrado.");
        }
        super.validar(artigo);
    }
}
