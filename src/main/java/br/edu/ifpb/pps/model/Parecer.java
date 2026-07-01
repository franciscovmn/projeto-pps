package br.edu.ifpb.pps.model;


import br.edu.ifpb.pps.enums.Veredito;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Parecer {
    private String id;
    private Artigo artigo;
    private Pesquisador revisor;
    private String contribuicoes;
    private String criticas;
    private Veredito veredito;
    private LocalDateTime dataConclusao;

    @Override
    public String toString() {
        return "{" +
                "id: \"" + id + "\"" +
                ", artigo: " + (artigo != null ? artigo.getTitulo() : "null") +
                ", revisor: " + (revisor != null ? revisor.getNome() : "null") +
                ", contribuicoes: \"" + contribuicoes + "\"" +
                ", criticas: \"" + criticas + "\"" +
                ", veredito: " + (veredito != null ? "\"" + veredito + "\"" : "null") +
                ", dataConclusao: " + (dataConclusao != null ? "\"" + dataConclusao + "\"" : "null") +
                "}";
    }
}