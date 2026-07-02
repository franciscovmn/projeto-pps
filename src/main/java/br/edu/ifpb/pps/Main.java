package br.edu.ifpb.pps;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifpb.pps.apresentacao.ApresentacaoConsole;
import br.edu.ifpb.pps.apresentacao.ServicoApresentacao;
import br.edu.ifpb.pps.chain.validacao.ValidadorBase;
import br.edu.ifpb.pps.chain.validacao.ValidadorCamposObrigatorios;
import br.edu.ifpb.pps.chain.validacao.ValidadorCoautoresCadastrados;
import br.edu.ifpb.pps.chain.validacao.ValidadorEventoAberto;
import br.edu.ifpb.pps.chain.validacao.ValidadorPrazo;
import br.edu.ifpb.pps.enums.CategoriaEvento;
import br.edu.ifpb.pps.enums.Veredito;
import br.edu.ifpb.pps.mediator.MediatorSistema;
import br.edu.ifpb.pps.model.AreaTematica;
import br.edu.ifpb.pps.model.Artigo;
import br.edu.ifpb.pps.model.Parecer;
import br.edu.ifpb.pps.model.Pesquisador;
import br.edu.ifpb.pps.modulos.ModuloCadastroPesquisador;
import br.edu.ifpb.pps.modulos.ModuloEvento;
import br.edu.ifpb.pps.modulos.ModuloSubmissaoArtigo;
import br.edu.ifpb.pps.notificacao.EmailService;
import br.edu.ifpb.pps.notificacao.ServicoNotificacao;
import br.edu.ifpb.pps.observer.EmailObservadorRevisor;
import br.edu.ifpb.pps.observer.NotificadorRevisor;
import br.edu.ifpb.pps.observer.RevisorConcreto;
import br.edu.ifpb.pps.modulos.ModuloRevisao;
import br.edu.ifpb.pps.service.DistribuicaoService;
import br.edu.ifpb.pps.strategy.distribuicao.DistribuicaoPorAfinidade;

public class Main {

    public static void main(String[] args) {
        ServicoApresentacao apresentacao = new ApresentacaoConsole();

        apresentacao.exibir("=================================================");
        apresentacao.exibir("  Sistema de Submissão de Artigos Científicos");
        apresentacao.exibir("  IFPB — Padrões de Projeto de Software");
        apresentacao.exibir("=================================================\n");

        // infra
        ModuloCadastroPesquisador moduloCadastro = new ModuloCadastroPesquisador();
        ModuloEvento moduloEvento = new ModuloEvento();
        ModuloSubmissaoArtigo moduloSubmissao = new ModuloSubmissaoArtigo();
        ServicoNotificacao servicoNotificacao = EmailService.configurarPorAmbiente(apresentacao);

        // chain: evento aberto - prazo - campos - coautores
        ValidadorBase cadeiaValidacao = new ValidadorEventoAberto(moduloEvento);
        cadeiaValidacao.setProximo(new ValidadorPrazo(moduloEvento))
                       .setProximo(new ValidadorCamposObrigatorios())
                       .setProximo(new ValidadorCoautoresCadastrados(moduloCadastro));

        // Observer: console + email
        NotificadorRevisor notificadorRevisor = new NotificadorRevisor();
        notificadorRevisor.registrar(new RevisorConcreto(apresentacao));
        notificadorRevisor.registrar(new EmailObservadorRevisor(servicoNotificacao));

        DistribuicaoService distribuicaoService = new DistribuicaoService(
                cadeiaValidacao,
                new DistribuicaoPorAfinidade(),
                notificadorRevisor);

        ModuloRevisao moduloRevisao = new ModuloRevisao();

        MediatorSistema mediator = new MediatorSistema(
                moduloCadastro, moduloEvento, moduloSubmissao,
                servicoNotificacao, distribuicaoService, moduloRevisao);

        // [1] Cadastro de pesquisadores
        apresentacao.exibir("[1] Cadastro de pesquisadores");
        moduloCadastro.cadastrarPesquisador("Ana Coordenadora", "ana@ifpb.edu.br", "123", "IFPB", true);
        moduloCadastro.cadastrarPesquisador("Bruno Autor", "bruno@ifpb.edu.br", "123", "IFPB", false);
        moduloCadastro.cadastrarPesquisador("Carla Revisora", "carla@ifpb.edu.br", "123", "UFPB", false,
                List.of(new AreaTematica("Engenharia de Software")));
        apresentacao.exibir("    Pesquisadores cadastrados: " + moduloCadastro.listarPesquisadores().size());

        // [2] Evento e áreas temáticas
        apresentacao.exibir("\n[2] Evento e áreas temáticas");
        moduloEvento.cadastrarEvento("SBES", "João Pessoa", "2026",
                LocalDateTime.now().plusDays(30), CategoriaEvento.FULL_PAPER);
        mediator.cadastrarAreaTematica("Engenharia de Software");
        mediator.cadastrarAreaTematica("Inteligência Artificial");
        mediator.iniciarEvento();
        apresentacao.exibir("    Evento \"" + moduloEvento.getEventoAtual().getNome() + "\" aberto para submissões.");

        // [3] Convite de revisor
        apresentacao.exibir("\n[3] Convite de revisor");
        mediator.convidarRevisor("carla@ifpb.edu.br");
        apresentacao.exibir("    Revisores: " + moduloCadastro.listarRevisores().size());

        // [4] Submissão de artigos
        apresentacao.exibir("\n[4] Submissão de artigos");
        Pesquisador autor = moduloCadastro.buscarPesquisadorPorEmail("bruno@ifpb.edu.br");
        List<AreaTematica> areas = List.of(new AreaTematica("Engenharia de Software"));

        Artigo aceito = mediator.submeterArtigo(
                "Padrões de Projeto na Prática", autor,
                "Um estudo sobre a aplicação de padrões GoF em sistemas acadêmicos.",
                "padroes-na-pratica.pdf", areas);

        Artigo rejeitado = mediator.submeterArtigo(
                "Uma Nova Linguagem de Programação", autor,
                "Proposta preliminar de uma linguagem de propósito geral.",
                "nova-linguagem.pdf", areas);

        apresentacao.exibir("    Artigos submetidos: " + aceito.getId() + ", " + rejeitado.getId());

        // [5] Distribuição (Chain + Strategy + Observer)
        apresentacao.exibir("\n[5] Distribuição dos artigos (Chain + Strategy + Observer)");
        mediator.distribuirArtigo(aceito);
        mediator.distribuirArtigo(rejeitado);
        apresentacao.exibir("    Status: " + aceito.getId() + " → " + aceito.getStatusArtigo().getNome());
        apresentacao.exibir("    Status: " + rejeitado.getId() + " → " + rejeitado.getStatusArtigo().getNome());

        // [6] Resultado das revisões (ModuloRevisao + State)
        apresentacao.exibir("\n[6] Resultado das revisões");
        Pesquisador revisora = moduloCadastro.buscarPesquisadorPorEmail("carla@ifpb.edu.br");

        mediator.registrarParecer(aceito, new Parecer("PAR-1", aceito, revisora,
                "Abordagem clara e bem fundamentada.", "Revisar a formatação das referências.",
                Veredito.ACEITO, LocalDateTime.now()));
        mediator.agregarResultado(aceito);

        mediator.registrarParecer(rejeitado, new Parecer("PAR-2", rejeitado, revisora,
                "Tema pertinente.", "Fundamentação teórica insuficiente.",
                Veredito.RECUSADO, LocalDateTime.now()));
        mediator.agregarResultado(rejeitado);

        apresentacao.exibir("    " + aceito.getId() + " → " + aceito.getStatusArtigo().getNome());
        apresentacao.exibir("    " + rejeitado.getId() + " → " + rejeitado.getStatusArtigo().getNome());

        // [7] Notificação em massa aos autores (Template Method)
        apresentacao.exibir("\n[7] Notificação em massa aos autores (Template Method)");
        int notificados = mediator.notificarAutores();
        apresentacao.exibir("    Autores notificados: " + notificados);

        // [8] Dashboard do evento
        apresentacao.exibir("\n[8] Dashboard do evento");
        apresentacao.exibir(mediator.obterEstatisticasDashboard());

        apresentacao.exibir("\n=================================================");
        apresentacao.exibir("  Fim da demonstração");
        apresentacao.exibir("=================================================");
    }
}
