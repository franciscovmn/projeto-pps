package br.edu.ifpb.pps.modulos;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Evento;
import br.edu.ifpb.pps.state.StatusEvento.StatusEventoAberto;
import br.edu.ifpb.pps.state.StatusEvento.StatusEventoFechado;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ModuloEvento {
    private final List<Evento> eventos = new ArrayList<Evento>();
    private List<AreaTematica> aerasTematicas = new ArrayList<AreaTematica>();
    private Evento eventoAtual;

    public void cadastrarEvento(Evento evento){
        eventos.add(evento);
        this.eventoAtual = evento;
    }

    public void cadastrarEvento(String nome, String cidade, String periodo, CategoriaEvento categoriaEvento){
        Evento evento = new Evento(nome, cidade, periodo, categoriaEvento);
        eventos.add(evento);
        this.eventoAtual = evento;
    }

    public void abrirEvento(){
        eventoAtual.setStatusEvento(new StatusEventoAberto(eventoAtual));
    }

    public void fecharEvento(){
        eventoAtual.setStatusEvento(new StatusEventoFechado(eventoAtual));
    }

    public void cadastrarAreaTematica(AreaTematica aeraTematica){
        this.aerasTematicas.add(aeraTematica);
    }

    public void cadastrarAreaTematica(String descricao) {
        AreaTematica areaTematica = new AreaTematica(descricao);
        this.aerasTematicas.add(areaTematica);
    }
}
