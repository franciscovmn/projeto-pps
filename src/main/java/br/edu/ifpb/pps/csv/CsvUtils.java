package br.edu.ifpb.pps.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvUtils {

    private static final String LIST_SEPARATOR = "\\|";
    private static final String LIST_JOINER = "|";

    private CsvUtils() {}

    public static String escape(String valor) {
        if (valor == null) return "";
        return valor.replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace(",", "\\,")
                .replace("|", "\\|");
    }

    public static String unescape(String valor) {
        if (valor == null) return "";
        return valor.replace("\\|", "|")
                .replace("\\,", ",")
                .replace("\\;", ";")
                .replace("\\\\", "\\");
    }

    public static String joinList(List<String> valores) {
        if (valores == null || valores.isEmpty()) return "";
        return valores.stream()
                .map(CsvUtils::escape)
                .collect(Collectors.joining(LIST_JOINER));
    }

    public static List<String> splitList(String valor) {
        List<String> resultado = new ArrayList<>();
        if (valor == null || valor.isBlank()) return resultado;

        // split simples por | (suficiente para o projeto)
        String[] partes = valor.split("\\|");
        for (String parte : partes) {
            resultado.add(unescape(parte));
        }
        return resultado;
    }

    public static String safe(String valor) {
        return valor == null ? "" : valor;
    }
}