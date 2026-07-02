package br.edu.ifpb.pps.mediator;

import br.edu.ifpb.pps.dashboard.Dashboard;
import br.edu.ifpb.pps.enums.PapelUsuario;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Parecer;
import br.edu.ifpb.pps.model.PerfilRevisor;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;
import br.edu.ifpb.pps.modulos.ModuloEvento;
import br.edu.ifpb.pps.modulos.ModuloSubmissaoArtigo;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;
import br.edu.ifpb.pps.service.DistribuicaoService;
import br.edu.ifpb.pps.template.NotificacaoAceitacao;
import br.edu.ifpb.pps.template.NotificacaoEmail;
import br.edu.ifpb.pps.template.NotificacaoRejeicao;

import java.util.ArrayList;
import java.util.List;

public class MediatorSistema {

    private final ModuloCadastroPesquisador moduloCadastro;
    private final ModuloEvento moduloEvento;
    private final ModuloSubmissaoArtigo moduloSubmissao;
    private final ServicoNotificacao servicoNotificacao;
    private final DistribuicaoService distribuicaoService;

    public MediatorSistema(ModuloCadastroPesquisador moduloCadastro, ModuloEvento moduloEvento,
                           ModuloSubmissaoArtigo moduloSubmissao, ServicoNotificacao servicoNotificacao,
                           DistribuicaoService distribuicaoService) {
        this.moduloCadastro = moduloCadastro;
        this.moduloEvento = moduloEvento;
        this.moduloSubmissao = moduloSubmissao;
        this.servicoNotificacao = servicoNotificacao;
        this.distribuicaoService = distribuicaoService;

        moduloCadastro.setMediator(this);
        moduloEvento.setMediator(this);
        moduloSubmissao.setMediator(this);
    }

    public void cadastrarAreaTematica(String descricao) {
        moduloEvento.cadastrarAreaTematica(descricao);
    }

    public void convidarRevisor(String email) {
        moduloCadastro.convidarRevisor(email);
    }

    public Artigo submeterArtigo(String titulo, Pesquisador autor, String resumo, String nomePDF,
                                 List<AreaTematica> areasTematicas) {
        Artigo artigo = moduloSubmissao.submeter(titulo, autor, resumo, nomePDF, areasTematicas);
        Dashboard.getInstance().incrementarArtigosSubmetidos();
        return artigo;
    }

    public void distribuirArtigo(Artigo artigo) {
        List<PerfilRevisor> candidatos = moduloCadastro.listarRevisores();
        distribuicaoService.distribuir(artigo, candidatos);
    }

    public void iniciarEvento() {
        moduloEvento.abrirEvento();
        Dashboard.getInstance().setEvento(moduloEvento.getEventoAtual());
    }

    public void encerrarEvento() {
        moduloEvento.fecharEvento();
    }

    public void notificar(String destinatario, String assunto, String mensagem) {
        servicoNotificacao.enviar(destinatario, assunto, mensagem);
    }

    public int notificarAutores() {
        String nomeEvento = moduloEvento.getEventoAtual().getNome();
        String nomeCoordenador = obterNomeCoordenador();
        int notificados = 0;

        for (Artigo artigo : moduloSubmissao.listarArtigos()) {
            NotificacaoEmail notificacao = montarNotificacao(artigo, nomeEvento, nomeCoordenador);

            if (notificacao == null) {
                continue;
            }

            servicoNotificacao.enviar(
                    artigo.getPesquisador().getEmail(),
                    "Resultado da submissão " + artigo.getId(),
                    notificacao.gerarNotificacao());
            notificados++;
        }

        return notificados;
    }

    public String obterEstatisticasDashboard() {
        return Dashboard.getInstance().imprimirEstatisticas();
    }

    private NotificacaoEmail montarNotificacao(Artigo artigo, String nomeEvento, String nomeCoordenador) {
        String resultado = artigo.getStatusArtigo().getNome();

        if ("ACEITO".equals(resultado)) {
            return new NotificacaoAceitacao(artigo.getPesquisador().getNome(), artigo.getId(),
                    artigo.getTitulo(), nomeEvento, nomeCoordenador, extrairPareceres(artigo));
        }

        if ("REJEITADO".equals(resultado)) {
            return new NotificacaoRejeicao(artigo.getPesquisador().getNome(), artigo.getId(),
                    artigo.getTitulo(), nomeEvento, nomeCoordenador, extrairPareceres(artigo));
        }

        return null;
    }

    private List<String[]> extrairPareceres(Artigo artigo) {
        List<String[]> pareceres = new ArrayList<>();

        for (Parecer parecer : artigo.getPareceres()) {
            pareceres.add(new String[]{parecer.getContribuicoes(), parecer.getCriticas()});
        }

        return pareceres;
    }

    private String obterNomeCoordenador() {
        return moduloCadastro.listarPesquisadores().stream()
                .filter(pesquisador -> pesquisador.getPapeisUsuario().contains(PapelUsuario.COORDENADOR))
                .map(Pesquisador::getNome)
                .findFirst()
                .orElse("Coordenação do Evento");
    }

    public ModuloCadastroPesquisador getModuloCadastro() {
        return moduloCadastro;
    }

    public ModuloEvento getModuloEvento() {
        return moduloEvento;
    }

    public ModuloSubmissaoArtigo getModuloSubmissao() {
        return moduloSubmissao;
    }
}
