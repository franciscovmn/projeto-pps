package br.edu.ifpb.pps.exception;

public class SubmissaoInvalidaException extends RuntimeException {
    public SubmissaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
