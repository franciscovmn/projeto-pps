package br.edu.ifpb.pps;

import br.edu.ifpb.pps.apresentacao.ApresentacaoConsole;
import br.edu.ifpb.pps.apresentacao.ServicoApresentacao;
import br.edu.ifpb.pps.chain.validacao.ValidadorAreaTematica;
import br.edu.ifpb.pps.chain.validacao.ValidadorArquivoPDF;
import br.edu.ifpb.pps.chain.validacao.ValidadorBase;
import br.edu.ifpb.pps.chain.validacao.ValidadorResumo;
import br.edu.ifpb.pps.chain.validacao.ValidadorTitulo;
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

public class Main {

    public static void main(String[] args) {
        ServicoApresentacao apresentacao = new ApresentacaoConsole();

        apresentacao.exibir("=================================================");
        apresentacao.exibir("  Sistema de Submissão de Artigos Científicos");
        apresentacao.exibir("  IFPB — Padrões de Projeto de Software");
        apresentacao.exibir("=================================================\n");

        ModuloCadastroPesquisador moduloCadastro = new ModuloCadastroPesquisador();
        ModuloEvento moduloEvento = new ModuloEvento();
        ModuloSubmissaoArtigo moduloSubmissao = new ModuloSubmissaoArtigo();
        ServicoNotificacao servicoNotificacao = EmailService.configurarPorAmbiente(apresentacao);

        ValidadorBase cadeiaValidacao = new ValidadorTitulo();
        cadeiaValidacao.setProximo(new ValidadorResumo())
                       .setProximo(new ValidadorAreaTematica())
                       .setProximo(new ValidadorArquivoPDF());
        moduloSubmissao.setValidador(cadeiaValidacao);

        MediatorSistema mediator = new MediatorSistema(moduloCadastro, moduloEvento, moduloSubmissao, servicoNotificacao);

        apresentacao.exibir("[1] Cadastro de pesquisadores");
        moduloCadastro.cadastrarPesquisador("Ana Coordenadora", "ana@ifpb.edu.br", "123", "IFPB", true);
        moduloCadastro.cadastrarPesquisador("Bruno Autor", "bruno@ifpb.edu.br", "123", "IFPB", false);
        moduloCadastro.cadastrarPesquisador("Carla Revisora", "carla@ifpb.edu.br", "123", "UFPB", false);
        apresentacao.exibir("    Pesquisadores cadastrados: " + moduloCadastro.listarPesquisadores().size());

        apresentacao.exibir("\n[2] Evento e áreas temáticas");
        moduloEvento.cadastrarEvento("SBES", "João Pessoa", "2026", LocalDateTime.now().plusDays(30), CategoriaEvento.FULL_PAPER);
        mediator.cadastrarAreaTematica("Engenharia de Software");
        mediator.cadastrarAreaTematica("Inteligência Artificial");
        mediator.iniciarEvento();
        apresentacao.exibir("    Evento \"" + moduloEvento.getEventoAtual().getNome() + "\" aberto para submissões.");

        apresentacao.exibir("\n[3] Convite de revisor");
        mediator.convidarRevisor("carla@ifpb.edu.br");
        apresentacao.exibir("    Revisores: " + moduloCadastro.listarRevisores().size());

        apresentacao.exibir("\n[4] Submissão de artigo");
        Pesquisador autor = moduloCadastro.buscarPesquisadorPorEmail("bruno@ifpb.edu.br");
        List<AreaTematica> areas = List.of(new AreaTematica("Engenharia de Software"));
        Artigo artigo = mediator.submeterArtigo(
                "Padrões de Projeto na Prática",
                autor,
                "Um estudo sobre a aplicação de padrões GoF em sistemas acadêmicos.",
                "padroes-na-pratica.pdf",
                areas);
        apresentacao.exibir("    Artigo submetido: " + artigo.getId() + " — " + artigo.getTitulo());

        apresentacao.exibir("\n[5] Notificação de aceite (Template Method + envio)");
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

        apresentacao.exibir("\n[6] Notificação de rejeição (Template Method + envio)");
        Artigo artigoRejeitado = mediator.submeterArtigo(
                "Uma Nova Linguagem de Programação",
                autor,
                "Proposta preliminar de uma linguagem de propósito geral.",
                "nova-linguagem.pdf",
                areas);
        apresentacao.exibir("    Artigo submetido: " + artigoRejeitado.getId() + " — " + artigoRejeitado.getTitulo());
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

        apresentacao.exibir("\n=================================================");
        apresentacao.exibir("  Fim da demonstração parcial");
        apresentacao.exibir("=================================================");
    }
}
