package br.edu.ifpb.pps.state.StatusArtigo;

import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Parecer;

public class StatusArtigoRejeitado implements StatusArtigo {
    private Artigo artigo;

    public StatusArtigoRejeitado(Artigo artigo){
        this.artigo = artigo;
    }

    @Override
    public String getNome() {
        return "REJEITADO";
    }

    @Override
    public String getParecer() {
        if (artigo.getPareceres().isEmpty()) {
            return "Nenhum parecer registrado.";
        }
        StringBuilder sb = new StringBuilder();
        for (Parecer p : artigo.getPareceres()) {
            sb.append("Revisor: ").append(p.getRevisor().getNome()).append("\n");
            sb.append("  Contribuições: ").append(p.getContribuicoes()).append("\n");
            sb.append("  Críticas: ").append(p.getCriticas()).append("\n");
            sb.append("  Veredito: ").append(p.getVeredito()).append("\n");
        }
        return sb.toString().trim();
    }
}
