package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.modulos.ModuloEvento;

public class ValidadorEventoAberto extends ValidadorBase {

    private final ModuloEvento moduloEvento;

    public ValidadorEventoAberto(ModuloEvento moduloEvento) {
        this.moduloEvento = moduloEvento;
    }

    @Override
    public void validar(Artigo artigo) {
        String status = moduloEvento.getEventoAtual().getStatusEvento().getNome();
        if (!"ABERTO".equals(status)) {
            throw new SubmissaoInvalidaException("Evento não está aberto para submissões.");
        }
        super.validar(artigo);
    }
}
