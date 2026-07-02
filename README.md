# Projeto PPS - Paper Submission Management System
> Para ver como colocar o projeto em funcionamento, acesse o arquivo: [como_rodar.md](como_rodar.md)

##### [Trello](https://trello.com/invite/b/6a390b1837afad71e3612e8c/ATTI10c16c12bf4e443c2a9f5eff2028f4beFA30A503/padr%C3%B5es-de-projeto)


## Disciplina

**Padrões de Projeto de Software** **Curso:** Sistemas para Internet

**Período:** 5º Período

**Professor:** Alex Sandro da Cunha Rêgo

---

# Equipe

| Integrante | Função |
| --- | --- |
| Francisco Viana Maia Neto | Desenvolvimento |
| Jonas Gabriel Sarmento de Figueiredo | Desenvolvimento |
| Murilo Maciel Rodrigues | Desenvolvimento |

---

# Descrição do Projeto

O **Paper Submission Management System** é um sistema de gerenciamento de submissão e avaliação de artigos científicos para eventos acadêmicos.

A aplicação permite que pesquisadores submetam artigos científicos, revisores realizem avaliações de forma anônima (*blind-review*) e coordenadores gerenciem todo o ciclo de submissão e revisão.

O objetivo principal é automatizar o fluxo de trabalho normalmente encontrado em congressos, simpósios e periódicos científicos.

---

# Objetivos

* Automatizar submissões de artigos científicos;
* Gerenciar eventos acadêmicos;
* Controlar revisores e áreas de especialidade;
* Distribuir artigos automaticamente;
* Garantir avaliação *blind-review*;
* Notificar autores e revisores;
* Aplicar princípios SOLID;
* Aplicar padrões de projeto em um cenário real.

---

# Funcionalidades e Padrões de Projeto

## RF01 - Inicialização de Evento

Permite criar um novo evento acadêmico contendo:

* Nome;
* Cidade;
* Data de início;
* Data de término;
* Categoria do evento;
* Prazo para submissão.

Categorias disponíveis:

* Full Paper;
* Short Paper;
* Demo.

> **Padrões de Projeto Adotados:**  
> * **Mediator:** Centralizado no `MediatorSistema` através do módulo de gerenciamento de eventos.
> * **State:** Gerencia o ciclo de vida do evento através das transições de estado (`Aberto` e `Fechado`).

---

## RF02 - Cadastro de Usuários

O sistema permite cadastro de pesquisadores através de:

* Nome;
* Email;
* Senha;
* Instituição.

Um mesmo usuário pode atuar como:

* Autor;
* Revisor;
* Coordenador.

> **Padrão de Projeto Adotado:**  
> * **Mediator:** Implementado via `ModuloCadastroPesquisador` integrado ao mediador central.

---

## RF03 - Cadastro de Áreas Temáticas

O coordenador pode cadastrar áreas de conhecimento utilizadas para:

* Classificação de artigos;
* Especialidades dos revisores.

Exemplos:

* Inteligência Artificial;
* Machine Learning;
* Engenharia de Software;
* Ciência de Dados;
* Visão Computacional.

> **Padrão de Projeto Adotado:** 
> * **Mediator:** Integrado via `ModuloEvento` respondendo à coordenação centralizada.

---

## RF04 - Comitê Técnico

O coordenador pode convidar pesquisadores para compor o comitê de revisão.

> **Padrão de Projeto Adotado:**
>  * **Mediator:** Fluxo gerenciado pelo `ModuloEvento` e centralizado pelo mediador.

---

## RF05 - Submissão de Artigos

Um artigo contém:

* ID;
* Título;
* Resumo;
* Autores;
* Coautores;
* Áreas temáticas;
* Categoria do evento;
* Data de submissão.

Status possíveis:

* Submetido;
* Em Revisão;
* Aceito;
* Rejeitado.

> **Padrões de Projeto Adotados:** 
> * **Mediator:** Encapsulado no `ModuloSubmissaoArtigo`.
> * **Chain of Responsibility:** Utilizado no `ValidadorSubmissao` para encadear as 4 validações obrigatórias (prazo, evento aberto, campos obrigatórios e coautores cadastrados).
> * **State:** Controla o ciclo de vida do artigo (`EstadoArtigo`: `Submetido`, `EmRevisao`, `Aceito`, `Rejeitado`, `Concluido`).

---

## RF06 - Distribuição Automática

O sistema distribui automaticamente artigos para revisores considerando:

* Afinidade temática;
* Balanceamento de carga (distribuição igualitária global);
* Ausência de conflito de interesse (revisor não revisa o próprio artigo);
* *Blind-review*.

> **Padrões de Projeto Adotados:** 
> * **Strategy:** Utilizado na variação de algoritmos através da interface `EstrategiaDistribuicao` e sua implementação concreta `DistribuicaoPorAfinidade`. O `DistribuicaoService` orquestra validação, seleção por afinidade e filtro de *blind-review* sem alterar a assinatura da estratégia.
> * **Observer:** Interface `ObservadorRevisor` com dois observadores concretos: `RevisorConcreto` (saída em console via `ServicoApresentacao`) e `EmailObservadorRevisor` (envio de convite por e-mail via `ServicoNotificacao`). O `NotificadorRevisor` notifica cada revisor atribuído individualmente.

---

## RF07 - Avaliação de Artigos

Cada avaliação contém:

### Contribuições

Descrição dos pontos positivos do artigo.

### Críticas

Descrição dos pontos negativos do artigo.

### Veredito

* Aceito;
* Fracamente Aceito;
* Fracamente Recusado;
* Recusado.

> **Padrões de Projeto Adotados:** 
> * **Mediator:** Implementado via `ModuloRevisao` (colleague do `MediatorSistema`) para coordenar o registro de pareceres e consolidação de resultados. O método `agregarResultado` conta vereditos positivos (`ACEITO`, `FRACAMENTE_ACEITO`) contra negativos e determina o resultado final por maioria.
> * **State:** Atualiza o ciclo de vida do artigo com base no resultado agregado, transitando para `StatusArtigoAceito` ou `StatusArtigoRejeitado`.

---

## RF08 - Dashboard

Exibe indicadores em tempo real do evento:

* Quantidade de artigos;
* Quantidade de revisores;
* Artigos avaliados;
* Artigos pendentes;
* Revisores responsáveis.

> **Padrão de Projeto Adotado:** 
> * **Singleton:** Utilizado para centralizar a instância do painel administrativo global (`Dashboard`).

---

## RF09 - Notificação dos Autores

Ao final do processo, os autores recebem notificações formais contendo:

* Resultado da avaliação;
* Pareceres dos revisores;
* Informações do evento.

> **Padrão de Projeto Adotado:** 
> * **Template Method:** Define a estrutura padrão de geração de mensagens através das variações `NotificacaoEmail`, `NotificacaoAceitacao` e `NotificacaoRejeicao`.

---

## RF10 - Gerador de Relatórios

Permite ao coordenador a exportação e geração de relatórios consolidados sobre o status do evento. Os relatórios podem ser extraídos nos formatos:

* CSV;
* HTML;
* TXT.

> **Padrão de Projeto Adotado:** 
> * **Template Method:** Utilizado para definir o esqueleto do algoritmo de exportação de dados, delegando às subclasses a formatação específica da saída (CSV, HTML ou TXT).

---

# Divisão de Tarefas

A distribuição do escopo de desenvolvimento foi realizada de maneira estratégica, equilibrando o volume de código e a complexidade algorítmica entre os membros do time.

## Murilo Maciel Rodrigues

**Foco Principal:** Domínio do sistema, mapeamento de estados, persistência de dados e administração inicial do evento (módulos de cadastro e coordenação).

**Componentes Desenvolvidos:**
* Modelagem completa das entidades estruturais do negócio (`Pesquisador`, `Artigo`, `Evento`, `AreaTematica`, `PerfilRevisor`) e enums (`PapelUsuario`, `CategoriaEvento`, `Veredito`).
* Controle do ciclo de vida e transições do `EstadoEvento` (`Aberto` e `Fechado`) via padrão **State**.
* Ciclo de vida completo do `EstadoArtigo` (`Submetido`, `EmRevisao`, `Aceito`, `Rejeitado`, `Concluido`) via padrão **State**.
* Implementação da classe `CsvLoader` para carga de dados e povoamento automático integrado aos repositórios.
* Painel administrativo centralizado (`Dashboard`) estruturado com o padrão **Singleton**.
* Implementação do `ModuloCadastroPesquisador` e `ModuloEvento`, acoplados ao barramento central.
* **RF10:** Desenvolvimento do componente gerador de relatórios flexível (CSV, HTML, TXT) estruturado sob o padrão **Template Method**.

**Entregas Técnicas:** Consolidação e organização final do documento `README.md`.

## Jonas Gabriel Sarmento de Figueiredo

**Foco Principal:** Camada de regras de negócio complexas, motores de validação, algoritmos de distribuição e fluxo de revisão.

**Componentes Desenvolvidos:**
* Implementação da validação de submissões por meio do padrão **chain**: classe abstrata `ValidadorBase` com quatro validadores concretos encadeados — `ValidadorEventoAberto` (verifica status do evento), `ValidadorPrazo` (verifica data limite), `ValidadorCamposObrigatorios` (título, resumo, PDF e área temática) e `ValidadorCoautoresCadastrados` (confirma cadastro de coautores no sistema). Cada validador recebe suas dependências via construtor sem alterar a assinatura `validar(Artigo)`.
* Arquitetura de seleção de algoritmos de alocação via padrão **Strategy**: interface `EstrategiaDistribuicao` e implementação concreta `DistribuicaoPorAfinidade`, que seleciona revisores comparando as áreas temáticas do artigo com as afinidades de cada revisor.
* Sistema de notificação de revisores via padrão **Observer**: subject `NotificadorRevisor`, interface `ObservadorRevisor` e dois observers concretos — `RevisorConcreto` (log em console) e `EmailObservadorRevisor` (convite por e-mail formatado).
* Serviço orquestrador `DistribuicaoService` compondo Chain, Strategy e Observer: valida, seleciona por afinidade, aplica filtro de *blind-review* (exclui autores do próprio artigo), transiciona o estado para `EmRevisao` e notifica os revisores atribuídos.
* `ModuloRevisao` como colleague do padrão **Mediator**: registra pareceres no artigo e, ao agregar resultado, conta vereditos positivos vs. negativos para disparar a transição de estado (`StatusArtigoAceito` ou `StatusArtigoRejeitado`) via padrão **State**.

**Entregas Técnicas:** Redação da seção técnica de regras do manual e revisão conceitual da lógica de negócio no diagrama de classes.

## Francisco Viana Maia Neto

**Foco Principal:** Arquitetura central de comunicação (*Mediator*), subsistema de submissão, infraestrutura de e-mails reais e integração geral da aplicação.

**Componentes Desenvolvidos:**
* Design e implementação da classe abstrata global `ModuloSistema` e da classe de controle central `MediatorSistema` (**Mediator** arquitetural do projeto).
* Desenvolvimento do `ModuloSubmissaoArtigo` gerenciando dados de arquivos (PDF e resumos) e chamando os validadores necessários.
* Definição da estrutura de mensagens eletrônicas através da classe base `NotificacaoEmail` e suas especializações (`NotificacaoAceitacao`, `NotificacaoRejeicao`) utilizando o padrão **Template Method**.
* Criação do contrato `ServicoNotificacao` e sua infraestrutura com o `EmailService` (usando JavaMail/Gmail) incluindo tratamento de falhas e contingências de rede.
* Refatoração abrangente da base de código legada para a completa eliminação do antigo padrão `Command` substituindo-o pela comunicação baseada em módulos.
* Construção do script completo de teste integrado e ponto de entrada da aplicação (`Main.java`) para a demonstração prática do sistema.

**Entregas Técnicas:** Coordenação, modelagem e consolidação do Diagrama de Classes integrado do ecossistema.

---

# Diagramas

## Diagrama de Classes

*(Seção para colocar o diagrama de classes)*

---

## Diagramas de Estado

*(Seção para colocar os diagramas de estado)*