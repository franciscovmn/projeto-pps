# Notificação de Revisores — Observer

## Responsável
Jonas

## Localização no projeto
- Observer: `src/main/java/br/edu/ifpb/pps/observer/`

---

## Integração com o restante do projeto

**nota** @Francisco @Murilo, o `NotificadorRevisor` vai ser recebido pelo `DistribuicaoService` no construtor. Ele é chamado como último passo da distribuição, depois que os revisores já foram selecionados e o blind-review aplicado.

---

Depois que a estratégia seleciona os revisores elegíveis para um artigo, o sistema avisa com esse modulo do observer. se precisar de um novo canal de notificação no é só criar mais um estilo`ObservadorRevisor` e registrar no `NotificadorRevisor`.

---

## Estrutura

**`ObservadorRevisor`** — interface Observer.

**`NotificadorRevisor`** — Publisher.

**`RevisorConcreto`** — ConcreteObserver. Ele imprime no console quando um revisor é atribuído a um artigo. 
**Logica de email vai ser aqui?**.
