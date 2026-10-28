package br.edu.ufjf.dcc168.jogo_aquario;

public class PeixeB extends IPeixe {

    @Override
    public Tipo getTipo() {
        return Tipo.B;
    }

    @Override
    public void incrementarReproducao() {
        reproducao++;
    }

}
