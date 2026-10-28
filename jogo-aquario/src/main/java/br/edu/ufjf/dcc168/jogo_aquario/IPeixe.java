package br.edu.ufjf.dcc168.jogo_aquario;

public abstract class IPeixe {

    protected int fome;
    protected int reproducao;

    public abstract Tipo getTipo();

    public int getFome() {
        return fome;
    }
    public void incrementarFome() {
        if (fome < 0) fome = 0;
        fome++;
    }
    public void decrementarFome() {
        if (fome > 0) fome = 0;
        fome--;
    }

    public int getReproducao() {
        return reproducao;
    }
    public abstract void incrementarReproducao();
    public void resetarReproducao() {
        reproducao = 0;
    }

}
