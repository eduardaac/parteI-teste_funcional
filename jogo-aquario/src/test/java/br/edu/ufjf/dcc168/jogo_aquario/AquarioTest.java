
package br.edu.ufjf.dcc168.jogo_aquario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AquarioTest {

    @Test
    public void testDimensoesMinimasValidas() {
        Aquario aquario = new Aquario(1, 1);

        assertEquals(1, aquario.getLinhas());
        assertEquals(1, aquario.getColunas());
    }

    @Test
    public void testDimensoesMaioresQueOMinimo() {
        Aquario aquario = new Aquario(2, 2);

        assertEquals(2, aquario.getLinhas());
        assertEquals(2, aquario.getColunas());
    }

    @Test
    public void testRejeitarZeroLinhas() {
        assertThrows(IllegalArgumentException.class,
            () -> new Aquario(0, 1));
    }

    @Test
    public void testRejeitarZeroColunas() {
        assertThrows(IllegalArgumentException.class,
            () -> new Aquario(1, 0));
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
                () -> new Aquario(dimensao[0], dimensao[1]));
        }
    }

    @Test
    public void testPosicionarPeixeNaPrimeiraCelula() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertTrue(aquario.posicionarPeixe(0, 0, peixe));
    }

    @Test
    public void testPosicionarPeixeNaUltimaCelula() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.B);

        assertTrue(aquario.posicionarPeixe(2, 2, peixe));
    }

    @Test
    public void testRejeitarLinhaAnteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertFalse(aquario.posicionarPeixe(-1, 0, peixe));
    }

    @Test
    public void testRejeitarLinhaPosteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertFalse(aquario.posicionarPeixe(3, 0, peixe));
    }

    @Test
    public void testRejeitarColunaAnteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertFalse(aquario.posicionarPeixe(0, -1, peixe));
    }

    @Test
    public void testRejeitarColunaPosteriorAoLimite() {
        Aquario aquario = new Aquario(3, 3);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertFalse(aquario.posicionarPeixe(0, 3, peixe));
    }

    @Test
    public void testRejeitarPosicionamentoEmCelulaOcupada() {
        Aquario aquario = new Aquario(1, 1);
        Peixe peixeA = new Peixe(Peixe.Tipo.A);
        Peixe peixeB = new Peixe(Peixe.Tipo.B);

        assertTrue(aquario.posicionarPeixe(0, 0, peixeA));
        assertFalse(aquario.posicionarPeixe(0, 0, peixeB));
    }

    @Test
    public void testContadorDeMovimentosConsecutivos() {
        int[] valores = {2, 3, 4};

        for (int valor : valores) {
            Peixe peixe = new Peixe(Peixe.Tipo.A);

            for (int i = 0; i < valor; i++) {
                peixe.registrarMovimento();
            }

            assertEquals(valor, peixe.getMovimentosConsecutivos());
        }
    }

    @Test
    public void testContadorDeIteracoesSemMovimento() {
        int[] valores = {2, 3, 4};

        for (int valor : valores) {
            Peixe peixe = new Peixe(Peixe.Tipo.A);

            for (int i = 0; i < valor; i++) {
                peixe.registrarSemMovimento();
            }

            assertEquals(valor, peixe.getIteracoesSemMovimento());
        }
    }

    @Test
    public void testContadorDePeixesComidos() {
        int[] valores = {2, 3, 4};

        for (int valor : valores) {
            Peixe peixe = new Peixe(Peixe.Tipo.B);

            for (int i = 0; i < valor; i++) {
                peixe.registrarPeixeComido();
            }

            assertEquals(valor, peixe.getPeixesComidos());
        }
    }

    @Test
    public void testContadorDeIteracoesSemComer() {
        int[] valores = {2, 3, 4};

        for (int valor : valores) {
            Peixe peixe = new Peixe(Peixe.Tipo.B);

            for (int i = 0; i < valor; i++) {
                peixe.registrarSemComer();
            }

            assertEquals(valor, peixe.getIteracoesSemComer());
        }
    }

    @Test
    public void testMovimentoZeraContadorDeParadas() {
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        peixe.registrarSemMovimento();
        peixe.registrarSemMovimento();
        peixe.registrarMovimento();

        assertEquals(0, peixe.getIteracoesSemMovimento());
        assertEquals(1, peixe.getMovimentosConsecutivos());
    }

    @Test
    public void testParadaZeraContadorDeMovimentos() {
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        peixe.registrarMovimento();
        peixe.registrarMovimento();
        peixe.registrarSemMovimento();

        assertEquals(0, peixe.getMovimentosConsecutivos());
        assertEquals(1, peixe.getIteracoesSemMovimento());
    }

    @Test
    public void testComerZeraContadorDeJejum() {
        Peixe peixe = new Peixe(Peixe.Tipo.B);

        peixe.registrarSemComer();
        peixe.registrarSemComer();
        peixe.registrarPeixeComido();

        assertEquals(0, peixe.getIteracoesSemComer());
        assertEquals(1, peixe.getPeixesComidos());
    }

    @Test
    public void testRejeitarPeixeNulo() {
        Aquario aquario = new Aquario(1, 1);

        assertFalse(aquario.posicionarPeixe(0, 0, null));
    }

    @Test
    public void testRejeitarTipoDePeixeNulo() {
        assertThrows(IllegalArgumentException.class,
            () -> new Peixe(null));
    }

    @Test
    public void testIndiceValidoNaMatrizMinima() {
        Aquario aquario = new Aquario(1, 1);
        Peixe peixe = new Peixe(Peixe.Tipo.A);

        assertTrue(aquario.posicionarPeixe(0, 0, peixe));
    }
}
