package br.edu.ifpb.pps.model;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.enums.StatusEvento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evento {
    private String nome;
    private String cidade;
    private String periodo;
    private CategoriaEvento categoriaEvento;
    private StatusEvento statusEvento; //TO-DO: trocar por padrão State

    public Evento(String nome, String cidade, String periodo, CategoriaEvento categoriaEvento) {
        this.nome = nome;
        this.cidade = cidade;
        this.periodo = periodo;
        this.categoriaEvento = categoriaEvento;
        this.statusEvento = StatusEvento.ABERTO;
    }
}
