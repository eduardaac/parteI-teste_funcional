# Jogo do Aquário — Teste Funcional

Projeto desenvolvido para a disciplina **DCC168 — Teste de Software**, da Universidade Federal de Juiz de Fora (UFJF), com foco na aplicação de técnicas de teste funcional baseadas na especificação.

## Objetivo

Especificar casos de teste para verificar o comportamento do Jogo do Aquário, considerando condições de entrada, regras de movimentação, reprodução, alimentação, morte por fome e encerramento da execução.

O trabalho aplica três critérios principais de teste funcional: **Particionamento em Classes de Equivalência, Análise de Valores-Limite e Grafo de Causa-Efeito**. A Tabela de Decisão é utilizada como apoio na definição dos cenários de teste.

## Especificação do sistema

O aquário é representado por uma matriz bidimensional de dimensões `M × N`, contendo peixes de dois tipos:

- **Peixe A:** alimenta-se de plâncton, movimenta-se para células livres, reproduz-se após atingir o limite de movimentações consecutivas e morre por falta de movimento.
- **Peixe B:** alimenta-se dos peixes A, movimenta-se em busca de alimento, reproduz-se após consumir uma quantidade definida de peixes A e morre por falta de alimentação.

A execução ocorre em iterações. Em cada iteração, os peixes realizam as ações permitidas pelas regras do jogo, e o estado atualizado do aquário é apresentado ao jogador.

O jogo termina quando não existem mais peixes do tipo B ou quando o jogador escolhe encerrar a execução. A pontuação corresponde ao número de iterações realizadas.

## Parâmetros de entrada

| Parâmetro | Descrição |
|---|---|
| `M` | Número de linhas da matriz |
| `N` | Número de colunas da matriz |
| `X` | Quantidade inicial de peixes do tipo A |
| `Y` | Quantidade inicial de peixes do tipo B |
| `RA` | Número de movimentações consecutivas para reprodução de A |
| `MA` | Número de iterações sem movimentação para morte de A |
| `RB` | Quantidade de peixes A consumidos para reprodução de B |
| `MB` | Número de iterações sem consumir A para morte de B |

Os parâmetros devem ser inteiros positivos, e a quantidade inicial de peixes deve respeitar a capacidade do aquário:

`X + Y ≤ M × N`

## Integrantes

- Bruno dos Santos Silva
- Darlan Henrique da Costa Silva
- Eduarda Araujo Carvalho
