package br.edu.ufjf.dcc168.jogo_aquario;

public class Game {
    public Game() {
        aquario = null;
        turn = 0;
    }
    private Aquario aquario;
    private int turn;

    public void newAquario(int linhas, int colunas, int peixesA, int peixesB, int reproducaoA, int mortalidadeA, int reproducaoB, int mortalidadeB) {
        aquario = new Aquario(linhas, colunas, peixesA, peixesB, reproducaoA, mortalidadeA, reproducaoB, mortalidadeB);
        System.out.println("AAAAAAA");
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
        if (aquario.contarPeixes(Tipo.B) <= 0) {
            System.out.println("Todos os peixes do tipo B morreram.");
            endGame();
            return;
        }
        turn++;
        aquario.iterarCiclo();
        System.out.println("Turno: " + turn);
        aquario.exibir();
    }

    //TODO:public void skipCycle() {}

    public void endGame() {
        System.out.println("Fim do jogo.");
        System.out.println("Total de turnos: " + turn);
        aquario = null;
    }

}
