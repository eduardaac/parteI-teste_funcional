package br.edu.ufjf.dcc168.jogo_aquario;

public class PeixeA extends IPeixe {

    @Override
    public Tipo getTipo() {
        return Tipo.A;
    }

    @Override
    public void incrementarReproducao() {
        if (fome > 0) {
            reproducao = fome;
        }
    }

}
