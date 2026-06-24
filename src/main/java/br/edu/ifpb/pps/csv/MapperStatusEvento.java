package br.edu.ifpb.pps.csv;

import br.edu.ifpb.pps.model.Evento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEvento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEventoAberto;
import br.edu.ifpb.pps.state.StatusEvento.StatusEventoFechado;

public class MapperStatusEvento {
    public static String paraCsv(StatusEvento estado) {
        return estado.getNome();
    }

    public static StatusEvento deCsv(String valor, Evento evento) {
        return switch (valor.toUpperCase()) {
            case "ABERTO" -> new StatusEventoAberto(evento);
            case "FECHADO" -> new StatusEventoFechado(evento);
            default -> throw new IllegalArgumentException("Estado inválido: " + valor);
        };
    }
}
