# Validador de Submissoes

## Responsável
Jonas

## Localizacao no projeto
- Cadeia: `src/main/java/br/edu/ifpb/pps/chain/validacao/`
- Exeption: `src/main/java/br/edu/ifpb/pps/exception/SubmissaoInvalidaException.java`

---

## integração com Francisco (nao mexi muito, só os imports)

*nota* @Francisco, como tu ja tinha feito as interfaces do `ValidadorSubmissao.java`, eu não mexi na interface, então o setNext só tem dentro da classe Base `ValidadorBase.java`.

O `ModuloSubmissaoArtigo` já tinha o método `setValidador(ValidadorSubmissao)` como ponto de entrada para a cadeia. A injeção foi feita no `Main.java` antes da criação do `MediatorSistema`. (provavelmente vai mudar no orquestrador dps)

---

Antes de registrar um artigo no sistema, a submissão passa por uma cadeia de validadores. Cada validador verifica uma regra de negócio. Se qualquer regra falhar, uma exceção é lançada e o artigo não é salvo. Se todas passarem, o artigo segue normalmente.

---

## Estrutura

**`ValidadorSubmissao`** — interface que define o contrato `validar(Artigo)`. É o único tipo que o restante do sistema conhece. O `ModuloSubmissaoArtigo` recebe uma instância dela sem saber que existe uma cadeia por baixo.

**`ValidadorBase`** — classe abstrata que implementa `ValidadorSubmissao`. Guarda a referência para o próximo validador da cadeia e implementa `validar` com o comportamento padrão de delegar para o próximo. Os validadores concretos estendem essa classe.

**Validadores concretos** — cada um sobrescreve `validar`, aplica sua regra, e chama `super.validar(artigo)` para passar para o próximo. São quatro:

| Classe | Regra verificada |
|---|---|
| `ValidadorTitulo` | Título não pode ser nulo nem vazio |
| `ValidadorResumo` | Resumo não pode ser vazio e deve ter ao menos 50 caracteres |
| `ValidadorAreaTematica` | Artigo deve ter ao menos uma área temática |
| `ValidadorArquivoPDF` | Nome do arquivo PDF não pode ser nulo nem vazio |

**`SubmissaoInvalidaException`** — exceção de runtime lançada por qualquer validador que reprovar. Está no pacote `exception` para centralizar as exceções do projeto.

---

## Como é executado

Quando `ModuloSubmissaoArtigo.submeter(...)` é chamado, ele invoca `validador.validar(artigo)`. Isso dispara a cadeia inteira na ordem: Título, Resumo, AreaTematica, PDF. A execução para na primeira falha.
