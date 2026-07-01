# Estratégia de Distribuição — Strategy

## Responsável
Jonas

## Localização no projeto
- Strategy: `src/main/java/br/edu/ifpb/pps/strategy/distribuicao/`

---

## Integração com o restante do projeto

**nota** @Francisco @Murilo, o Context do Strategy é o `DistribuicaoService`, que ainda vai ser criado. Por enquanto a interface e a implementação concreta já estão prontas para serem injetadas nele quando chegar a hora.

---

Depois que a cadeia de validação aprova um artigo, o sistema precisa decidir quais revisores serão atribuídos a ele. Essa decisão é delegada para uma estratégia. por enquanto existe uma estratégia concreta só, por afinidade temática.

---

## Estrutura

**`EstrategiaDistribuicao`** — interface Strategy. Define o contrato `distribuir(Artigo, List<PerfilRevisor>)`, que recebe o artigo e a lista de revisores disponíveis e retorna os revisores selecionados.

**`DistribuicaoPorAfinidade`** — ConcreteStrategy. Filtra os candidatos mantendo apenas os revisores cuja lista de afinidades tem ao menos uma área temática em comum com as áreas do artigo. A comparação é feita pela descrição da área, porque `AreaTematica` não implementa `equals`.

**Context** — será o `DistribuicaoService` (último item do quadro). Ele receberá `EstrategiaDistribuicao` no construtor e delegará a seleção de revisores sem saber qual implementação está por baixo.

---

## Como é executado

O `DistribuicaoService` chamará `estrategia.distribuir(artigo, candidatos)`. O resultado é a lista de revisores elegíveis, que em seguida passa pela regra de blind-review antes de ser atribuída ao artigo.

Trocar a estratégia de distribuição no futuro é só passar uma implementação diferente de `EstrategiaDistribuicao` para o `DistribuicaoService`.
