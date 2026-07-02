package br.edu.ifpb.pps.chain.validacao;

import br.edu.ifpb.pps.exception.SubmissaoInvalidaException;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidadorCoautoresCadastrados extends ValidadorBase {

    private final ModuloCadastroPesquisador moduloCadastro;

    public ValidadorCoautoresCadastrados(ModuloCadastroPesquisador moduloCadastro) {
        this.moduloCadastro = moduloCadastro;
    }

    @Override
    public void validar(Artigo artigo) {
        Set<String> emailsCadastrados = moduloCadastro.listarPesquisadores().stream()
                .map(Pesquisador::getEmail)
                .collect(Collectors.toSet());

        for (Pesquisador coautor : artigo.getCoAutores()) {
            if (!emailsCadastrados.contains(coautor.getEmail())) {
                throw new SubmissaoInvalidaException(
                        "Coautor não cadastrado no sistema: " + coautor.getNome());
            }
        }
        super.validar(artigo);
    }
}
