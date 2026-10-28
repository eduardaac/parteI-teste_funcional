package br.edu.ufjf.dcc168.jogo_aquario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IPeixeTest {
    IPeixe peixe;

//region PeixeA

    @Test
    public void testIncrementarFomePeixeA() {
        peixe = new PeixeA();
        int fomeInicial = peixe.getFome();
        peixe.incrementarFome();
        assertTrue(peixe.getFome() > fomeInicial);
        assertEquals(1, peixe.getFome());
    }

    @Test
    public void testIncrementarReproducaoPeixeA() {
        peixe = new PeixeA();
        int reproducaoInicial = peixe.getReproducao();
        peixe.incrementarFome(); // RA baseado em MA
        peixe.incrementarReproducao();
        assertTrue(peixe.getReproducao() > reproducaoInicial);
        assertEquals(1, peixe.getReproducao());
    }

    @Test
    public void testDecrementarFomePeixeA() {
        peixe = new PeixeA();
        peixe.incrementarFome();
        int fomeInicial = peixe.getFome();
        peixe.decrementarFome();
        assertTrue(peixe.getFome() < fomeInicial);
        assertEquals(-1, peixe.getFome());
    }

//endregion PeixeA

//region PeixeB

    @Test
    public void testIncrementarFomePeixeB() {
        peixe = new PeixeB();
        int fomeInicial = peixe.getFome();
        peixe.incrementarFome();
        assertTrue(peixe.getFome() > fomeInicial);
        assertEquals(1, peixe.getFome());
    }

    @Test
    public void testIncrementarReproducaoPeixeB() {
        peixe = new PeixeB();
        int reproducaoInicial = peixe.getReproducao();
        peixe.incrementarReproducao();
        assertTrue(peixe.getReproducao() > reproducaoInicial);
        assertEquals(1, peixe.getReproducao());
    }

    @Test
    public void testDecrementarFomePeixeB() {
        peixe = new PeixeB();
        peixe.incrementarFome();
        int fomeInicial = peixe.getFome();
        peixe.decrementarFome();
        assertTrue(peixe.getFome() < fomeInicial);
        assertEquals(-1, peixe.getFome());
    }

//endregion PeixeB

}
