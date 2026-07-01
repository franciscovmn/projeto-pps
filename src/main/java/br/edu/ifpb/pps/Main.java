package br.edu.ifpb.pps;

import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.mediator.MediatorSistema;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;
import br.edu.ifpb.pps.modulos.ModuloEvento;
import br.edu.ifpb.pps.modulos.ModuloSubmissaoArtigo;
import br.edu.ifpb.pps.notificacao.EmailService;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;
import br.edu.ifpb.pps.template.NotificacaoAceitacao;
import br.edu.ifpb.pps.template.NotificacaoEmail;
import br.edu.ifpb.pps.template.NotificacaoRejeicao;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ponto de entrada do Sistema de Submissão e Avaliação de Artigos Científicos.
 *
 * <p>Disciplina: Padrões de Projeto de Software</p>
 * <p>Professor: Alex Sandro da Cunha Rêgo</p>
 * <p>Instituto Federal da Paraíba — IFPB</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Sistema de Submissão de Artigos Científicos");
        System.out.println("  IFPB — Padrões de Projeto de Software");
        System.out.println("=================================================\n");

        ModuloCadastroPesquisador moduloCadastro = new ModuloCadastroPesquisador();
        ModuloEvento moduloEvento = new ModuloEvento();
        ModuloSubmissaoArtigo moduloSubmissao = new ModuloSubmissaoArtigo();
        ServicoNotificacao servicoNotificacao = new EmailService();

        MediatorSistema mediator = new MediatorSistema(moduloCadastro, moduloEvento, moduloSubmissao, servicoNotificacao);

        System.out.println("[1] Cadastro de pesquisadores");
        moduloCadastro.cadastrarPesquisador("Ana Coordenadora", "ana@ifpb.edu.br", "123", "IFPB", true);
        moduloCadastro.cadastrarPesquisador("Bruno Autor", "bruno@ifpb.edu.br", "123", "IFPB", false);
        moduloCadastro.cadastrarPesquisador("Carla Revisora", "carla@ifpb.edu.br", "123", "UFPB", false);
        System.out.println("    Pesquisadores cadastrados: " + moduloCadastro.listarPesquisadores().size());

        System.out.println("\n[2] Evento e áreas temáticas");
        moduloEvento.cadastrarEvento("SBES", "João Pessoa", "2026", LocalDateTime.now().plusDays(30), CategoriaEvento.FULL_PAPER);
        mediator.cadastrarAreaTematica("Engenharia de Software");
        mediator.cadastrarAreaTematica("Inteligência Artificial");
        mediator.iniciarEvento();
        System.out.println("    Evento \"" + moduloEvento.getEventoAtual().getNome() + "\" aberto para submissões.");

        System.out.println("\n[3] Convite de revisor");
        mediator.convidarRevisor("carla@ifpb.edu.br");
        System.out.println("    Revisores: " + moduloCadastro.listarRevisores().size());

        System.out.println("\n[4] Submissão de artigo");
        Pesquisador autor = moduloCadastro.buscarPesquisadorPorEmail("bruno@ifpb.edu.br");
        List<AreaTematica> areas = List.of(new AreaTematica("Engenharia de Software"));
        Artigo artigo = mediator.submeterArtigo(
                "Padrões de Projeto na Prática",
                autor,
                "Um estudo sobre a aplicação de padrões GoF em sistemas acadêmicos.",
                "padroes-na-pratica.pdf",
                areas);
        System.out.println("    Artigo submetido: " + artigo.getId() + " — " + artigo.getTitulo());

        System.out.println("\n[5] Notificação de aceite (Template Method + envio)");
        Pesquisador coordenador = moduloCadastro.buscarPesquisadorPorEmail("ana@ifpb.edu.br");
        List<String[]> pareceresAceite = List.of(
                new String[]{"Abordagem clara e bem fundamentada.", "Revisar a formatação das referências."},
                new String[]{"Exemplos práticos relevantes.", "Ampliar a discussão dos resultados."}
        );
        NotificacaoEmail aceite = new NotificacaoAceitacao(
                autor.getNome(),
                artigo.getId(),
                artigo.getTitulo(),
                moduloEvento.getEventoAtual().getNome(),
                coordenador.getNome(),
                pareceresAceite);
        mediator.notificar(autor.getEmail(), "Resultado da submissão " + artigo.getId(), aceite.gerarNotificacao());

        System.out.println("\n[6] Notificação de rejeição (Template Method + envio)");
        Artigo artigoRejeitado = mediator.submeterArtigo(
                "Uma Nova Linguagem de Programação",
                autor,
                "Proposta preliminar de uma linguagem de propósito geral.",
                "nova-linguagem.pdf",
                areas);
        System.out.println("    Artigo submetido: " + artigoRejeitado.getId() + " — " + artigoRejeitado.getTitulo());
        List<String[]> pareceresRejeicao = List.of(
                new String[]{"Tema pertinente.", "Fundamentação teórica insuficiente.", "Ausência de avaliação experimental."},
                new String[]{"Texto bem escrito.", "Contribuição pouco original."}
        );
        NotificacaoEmail rejeicao = new NotificacaoRejeicao(
                autor.getNome(),
                artigoRejeitado.getId(),
                artigoRejeitado.getTitulo(),
                moduloEvento.getEventoAtual().getNome(),
                coordenador.getNome(),
                pareceresRejeicao);
        mediator.notificar(autor.getEmail(), "Resultado da submissão " + artigoRejeitado.getId(), rejeicao.gerarNotificacao());

        System.out.println("\n=================================================");
        System.out.println("  Fim da demonstração parcial");
        System.out.println("=================================================");
    }
}
