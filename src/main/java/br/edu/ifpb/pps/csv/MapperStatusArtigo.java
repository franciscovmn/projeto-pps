package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.state.StatusArtigo.*;

public class MapperStatusArtigo {
    public static String paraCsv(StatusArtigo estado) {
        return estado.getNome();
    }

    public static StatusArtigo deCsv(String valor, Artigo artigo) {
        return switch (valor.toUpperCase()) {
            case "SUBMETIDO" -> new StatusArtigoSubmetido(artigo);
            case "EM_REVISAO" -> new StatusArtigoRevisao(artigo);
            case "ACEITO" -> new StatusArtigoAceito(artigo);
            case "REJEITADO" -> new StatusArtigoRejeitado(artigo);
            default -> throw new IllegalArgumentException("Estado inválido: " + valor);
        };
    }
}