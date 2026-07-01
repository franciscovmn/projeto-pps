package br.edu.ifpb.pps.modulos;

import br.edu.ifpb.pps.mediator.ModuloSistema;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.validacao.ValidadorSubmissao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuloSubmissaoArtigo extends ModuloSistema {

    private final Map<String, Artigo> artigos = new HashMap<>();
    private int contador = 1;
    private ValidadorSubmissao validador;

    public void setValidador(ValidadorSubmissao validador) {
        this.validador = validador;
    }

    public Artigo submeter(String titulo, Pesquisador autor, String resumo, String nomePDF, List<AreaTematica> areasTematicas) {
        return submeter(titulo, autor, resumo, nomePDF, new ArrayList<>(), areasTematicas);
    }

    public Artigo submeter(String titulo, Pesquisador autor, String resumo, String nomePDF,
                           List<Pesquisador> coAutores, List<AreaTematica> areasTematicas) {
        String id = gerarId();
        Artigo artigo = new Artigo(id, titulo, autor, resumo, nomePDF, coAutores, areasTematicas);

        if (validador != null) {
            validador.validar(artigo);
        }

        artigos.put(id, artigo);
        autor.getArtigosSubmetidos().add(artigo);

        return artigo;
    }

    public Artigo buscarPorId(String id) {
        Artigo artigo = artigos.get(id);

        if (artigo == null) {
            throw new RuntimeException("Artigo não encontrado.");
        }

        return artigo;
    }

    public List<Artigo> listarArtigos() {
        return new ArrayList<>(artigos.values());
    }

    private String gerarId() {
        return "ART-" + contador++;
    }
}
