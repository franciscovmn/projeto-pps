# Projeto PPS - Paper Submission Management System 

##### [Trello](https://trello.com/invite/b/6a390b1837afad71e3612e8c/ATTI10c16c12bf4e443c2a9f5eff2028f4beFA30A503/padr%C3%B5es-de-projeto)


## Disciplina

**Padrões de Projeto de Software**

**Curso:** Sistemas para Internet

**Período:** 5º Período

**Professor:** Alex Sandro da Cunha Rêgo

---

# Equipe

| Integrante                           | Função          |
|--------------------------------------| --------------- |
| Francisco Viana Maia Neto            | Desenvolvimento |
| Jonas Gabriel Sarmento de Figueiredo | Desenvolvimento |
| Murilo Maciel Rodrigues              | Desenvolvimento |

---

# Descrição do Projeto

O **Paper Submission Management System** é um sistema de gerenciamento de submissão e avaliação de artigos científicos para eventos acadêmicos.

A aplicação permite que pesquisadores submetam artigos científicos, revisores realizem avaliações de forma anônima (blind-review) e coordenadores gerenciem todo o ciclo de submissão e revisão.

O objetivo principal é automatizar o fluxo de trabalho normalmente encontrado em congressos, simpósios e periódicos científicos.

---

# Objetivos

* Automatizar submissões de artigos científicos;
* Gerenciar eventos acadêmicos;
* Controlar revisores e áreas de especialidade;
* Distribuir artigos automaticamente;
* Garantir avaliação blind-review;
* Notificar autores e revisores;
* Aplicar princípios SOLID;
* Aplicar padrões de projeto em um cenário real.

---

# Funcionalidades

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

---

## RF04 - Comitê Técnico

O coordenador pode convidar pesquisadores para compor o comitê de revisão.

O convite pode ser:

* Aceito;
* Recusado.

Ao aceitar, o pesquisador informa suas áreas de especialidade.

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

---

## RF06 - Distribuição Automática

O sistema distribui automaticamente artigos para revisores considerando:

* Afinidade temática;
* Balanceamento de carga;
* Ausência de conflito de interesse;
* Blind-review.

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

---

## RF08 - Dashboard

Exibe indicadores do evento:

* Quantidade de artigos;
* Quantidade de revisores;
* Artigos avaliados;
* Artigos pendentes;
* Revisores responsáveis.

---

## RF09 - Notificação dos Autores

Ao final do processo, os autores recebem:

* Resultado da avaliação;
* Pareceres dos revisores;
* Informações do evento.

---

## RF10 - Novo Requisito