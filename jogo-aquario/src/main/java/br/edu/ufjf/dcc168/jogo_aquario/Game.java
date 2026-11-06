package br.edu.ufjf.dcc168.jogo_aquario;

public class Game {
    public Game() {
        aquario = null;
        turn = 0;
    }

    private Aquario aquario;
    private int turn;

    public void newAquario(int linhas, int colunas, int peixesA, int peixesB, int reproducaoA, int mortalidadeA,
            int reproducaoB, int mortalidadeB) {
        aquario = new Aquario(linhas, colunas, peixesA, peixesB, reproducaoA, mortalidadeA, reproducaoB, mortalidadeB);
        turn = 0;
    }

    public void newAquario(Aquario aquario) {
        this.aquario = aquario;
        turn = 0;
    }

    public void nextCycle() {
        if (aquario == null) {
            System.out.println("Aquário não inicializado.");
            return;
        }
        if (endGame() > 0)
            return;
        turn++;
        aquario.iterarCiclo();
        System.out.println("Turno: " + turn);
        exibir();
    }

    public void skipCycle() {
        if (aquario == null) {
            System.out.println("Aquário não inicializado.");
            return;
        }
        if (endGame() > 0)
            return;
        turn++;
        aquario.forcarProximaIteracao();
        System.out.println("Turno: " + turn);
        exibir();
    }

    private void exibir() {
        for (int i = 0; i < aquario.getLinhas(); i++) {
            for (int j = 0; j < aquario.getColunas(); j++) {
                IPeixe peixe = aquario.getPeixe(i, j);
                System.out.print(peixe == null ? ". " : peixe.getTipo() + " ");
            }
            System.out.println();
        }
    }

    public int endGame() {
        if (aquario.contarPeixes(Tipo.B) <= 0) {
            System.out.println("Todos os peixes do tipo B morreram.");
            System.out.println("Fim do jogo.");
            System.out.println("Total de turnos: " + turn);
            aquario = null;
            return turn;
        }
        return 0;
    }

}
