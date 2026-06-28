package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEvento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEventoAberto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Evento {
    private String nome;
    private String cidade;
    private String periodo;
    private CategoriaEvento categoriaEvento;
    private StatusEvento statusEvento; // OBS: mais pra frente é bom mover o statusEvento da entidade para a classe que controla os eventos
    private List<Artigo> artigos = new ArrayList<Artigo>();

    public Evento(String nome, String cidade, String periodo, CategoriaEvento categoriaEvento) {
        this.nome = nome;
        this.cidade = cidade;
        this.periodo = periodo;
        this.categoriaEvento = categoriaEvento;
        this.statusEvento = new StatusEventoAberto(this);
    }

    public void receberArtigo(Artigo artigo) {
        statusEvento.receberArtigo(artigo);
    }
}
