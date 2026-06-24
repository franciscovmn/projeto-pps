package br.edu.ifpb.pps.csv;

import java.io.IOException;
import java.util.List;

public interface CsvRepository<T> {
    void salvar(List<T> dados) throws IOException;
    List<T> carregar() throws IOException;
}