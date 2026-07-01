package br.edu.ifpb.pps.apresentacao;

import java.io.PrintStream;

public class ApresentacaoConsole implements ServicoApresentacao {

    private final PrintStream saida;

    public ApresentacaoConsole() {
        this(System.out);
    }

    public ApresentacaoConsole(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public void exibir(String mensagem) {
        saida.println(mensagem);
    }
}
