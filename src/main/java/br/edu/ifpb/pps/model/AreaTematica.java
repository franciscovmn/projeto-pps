package br.edu.ifpb.pps.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaTematica {
    private String descricao;

    public AreaTematica(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "{descricao: \"" + descricao + "\"}";
    }
}
