
package br.edu.ufjf.dcc168.jogo_aquario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AquarioTest {

    @Test
    public void testDimensoesMinimasValidas() {
        Aquario aquario = new Aquario(1, 1, 1, 1, 1, 1, 1, 1);

        assertEquals(1, aquario.getLinhas());
        assertEquals(1, aquario.getColunas());
    }

    @Test
    public void testDimensoesMaioresQueOMinimo() {
        Aquario aquario = new Aquario(2, 2, 1, 1, 1, 1, 1, 1);

        assertEquals(2, aquario.getLinhas());
        assertEquals(2, aquario.getColunas());
    }

    @Test
    public void testRejeitarZeroLinhas() {
        assertThrows(IllegalArgumentException.class,
            () -> new Aquario(0, 1, 1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testRejeitarZeroColunas() {
        assertThrows(IllegalArgumentException.class,
            () -> new Aquario(1, 0, 1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testRejeitarDimensoesNegativas() {
        int[][] dimensoes = {
            {-1, 1},
            {1, -1},
            {-1, -1}
        };

        for (int[] dimensao : dimensoes) {
            assertThrows(IllegalArgumentException.class,
                () -> new Aquario(dimensao[0], dimensao[1], 1, 1, 1, 1, 1, 1));
        }
    }

    @Test
    public void testPosicionarPeixeNaPrimeiraCelula() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertTrue(aquario.posicionarPeixe(0, 0, peixe));
    }

    @Test
    public void testPosicionarPeixeNaUltimaCelula() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertTrue(aquario.posicionarPeixe(2, 2, peixe));
    }

    @Test
    public void testRejeitarLinhaAnteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertFalse(aquario.posicionarPeixe(-1, 0, peixe));
    }

    @Test
    public void testRejeitarLinhaPosteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertFalse(aquario.posicionarPeixe(3, 0, peixe));
    }

    @Test
    public void testRejeitarColunaAnteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertFalse(aquario.posicionarPeixe(0, -1, peixe));
    }

    @Test
    public void testRejeitarColunaPosteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertFalse(aquario.posicionarPeixe(0, 3, peixe));
    }

    @Test
    public void testRejeitarPosicionamentoEmCelulaOcupada() {
        Aquario aquario = new Aquario(1, 1, 1, 1, 1, 1, 1, 1);
        IPeixe peixeA = new PeixeA();
        IPeixe peixeB = new PeixeB();

        assertTrue(aquario.posicionarPeixe(0, 0, peixeA));
        assertFalse(aquario.posicionarPeixe(0, 0, peixeB));
    }

    @Test
    public void testRejeitarPeixeNulo() {
        Aquario aquario = new Aquario(1, 1, 1, 1, 1, 1, 1, 1);

        assertFalse(aquario.posicionarPeixe(0, 0, null));
    }

    @Test
    public void testIndiceValidoNaMatrizMinima() {
        Aquario aquario = new Aquario(1, 1, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();

        assertTrue(aquario.posicionarPeixe(0, 0, peixe));
    }

    @Test 
    public void testMoverPeixeA() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();
        aquario.posicionarPeixe(0, 0, peixe);

        assertTrue(aquario.moverPeixe(0, 0, 1, 1));
    }

    @Test
    public void testMoverPeixeAParaCelulaInvalida() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();
        aquario.posicionarPeixe(0, 0, peixe);

        assertFalse(aquario.moverPeixe(0, 0, -1, -1));
    }

    @Test 
    public void testMoverPeixeAParaCelulaOcupada() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixeA = new PeixeA();
        IPeixe peixeB = new PeixeB();
        aquario.posicionarPeixe(0, 0, peixeA);
        aquario.posicionarPeixe(1, 1, peixeB);

        assertFalse(aquario.moverPeixe(0, 0, 1, 1));
    }

    @Test 
    public void testMoverPeixeB() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeB();
        aquario.posicionarPeixe(0, 0, peixe);

        assertTrue(aquario.moverPeixe(0, 0, 1, 1));
    }

    @Test
    public void testPeixeBComePeixeA() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixeA = new PeixeA();
        IPeixe peixeB = new PeixeB();
        aquario.posicionarPeixe(0, 0, peixeA);
        aquario.posicionarPeixe(1, 1, peixeB);

        assertTrue(aquario.moverPeixe(1, 1, 0, 0));
    }

    @Test
    public void testPeixeBNaoComePeixeB() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe1 = new PeixeB();
        IPeixe peixe2 = new PeixeB();
        aquario.posicionarPeixe(0, 0, peixe1);
        aquario.posicionarPeixe(1, 1, peixe2);

        assertFalse(aquario.moverPeixe(0, 0, 1, 1));
    }

    @Test
    public void testMoverPeixeNull() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);

        assertFalse(aquario.moverPeixe(0, 0, 1, 1));
    }

    @Test 
    public void testMoverPeixeDeCelulaInvalida() {
        Aquario aquario = new Aquario(3, 3, 1, 1, 1, 1, 1, 1);
        IPeixe peixe = new PeixeA();
        aquario.posicionarPeixe(0, 0, peixe);

        assertFalse(aquario.moverPeixe(-1, -1, 1, 1));
    }

}
