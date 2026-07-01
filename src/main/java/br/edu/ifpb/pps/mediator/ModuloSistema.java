package br.edu.ifpb.pps.mediator;

public abstract class ModuloSistema {

    protected MediatorSistema mediator;

    public void setMediator(MediatorSistema mediator) {
        this.mediator = mediator;
    }
}
