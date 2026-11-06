package br.edu.ufjf.dcc168.jogo_aquario;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSetFunc {

    /* TODO: Casos de teste documentados ainda sem teste:
     * CT08 - Reprodução do peixe A quando o limite RA é atingido, mas não há espaço.
     * CT09 - Peixe A permanece vivo no limite MA - 1 de iterações sem movimento.
     * CT12 - Peixe B move-se para uma célula livre quando não há peixe A adjacente.
     * CT14 - Reprodução do peixe B impedida pela presença de outro peixe B adjacente.
     * CT15 - Peixe B permanece vivo no limite MB - 1 de iterações sem alimentação.
     */

    // region Validação de Inicialização

    @Test
    public void testGameValido() {
        Game game = new Game();

        assertDoesNotThrow(() -> game.newAquario(1, 2, 1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testGameDimensaoInvalida() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class,
                () -> game.newAquario(0, 5, 1, 1, 2, 2, 2, 2));
    }

    @Test
    public void testPeixesEmExcesso() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class,
                () -> game.newAquario(2, 2, 3, 2, 2, 2, 2, 2));
    }

    @Test
    public void testGameParametrosInvalidos() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class,
                () -> game.newAquario(5, 5, 2, 2, 0, 2, 2, 2));
    }

    // endregion

    // region Regras e Ciclo de Vida Peixe A

    @Test
    public void testMovimentacaoPeixeA() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 1, 1, 1);
        aquario.posicionarPeixe(1, 1, new PeixeA());
        assertTrue(aquario.moverPeixe(1, 1, 1, 2));
        assertNull(aquario.getPeixe(1, 1));
        assertNotNull(aquario.getPeixe(1, 2));
    }

    @Test
    public void testReproducaoPeixeA() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 2, 2, 2);
        aquario.posicionarPeixe(0, 0, new PeixeA());
        aquario.moverPeixe(0, 0, 0, 1);
        aquario.moverPeixe(0, 1, 1, 1);
        assertNull(aquario.getPeixe(0, 0));
        assertNotNull(aquario.getPeixe(0, 1));
        assertNotNull(aquario.getPeixe(1, 1));
    }

    @Test
    public void testMortalidadePeixeA() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 2, 1, 1);
        aquario.posicionarPeixe(0, 0, new PeixeA());
        assertNotNull(aquario.getPeixe(0, 0)); // parado 0

        aquario.forcarProximaIteracao();
        assertNotNull(aquario.getPeixe(0, 0)); // parado 1

        aquario.forcarProximaIteracao();
        assertNull(aquario.getPeixe(0, 0)); // parado 2
    }

    // endregion

    // region Regras e Ciclo de Vida Peixe B

    @Test
    public void testAlimentacaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 1, 1, 1);
        aquario.posicionarPeixe(1, 1, new PeixeB());
        aquario.posicionarPeixe(1, 2, new PeixeA());

        aquario.moverPeixe(1, 1, 1, 2);
        assertNull(aquario.getPeixe(1, 1));

        assertEquals(Tipo.B, aquario.getPeixe(1, 2).getTipo());
    }

    @Test
    public void testMovimentacaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 2, 2, 2, 2);
        aquario.posicionarPeixe(1, 1, new PeixeB());

        assertTrue(aquario.moverPeixe(1, 1, 1, 2));

        assertNull(aquario.getPeixe(1, 1));
        assertEquals(aquario.getPeixe(1, 2).getFome(), -1);
    }

    @Test
    public void testReproducaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 2, 2, 2, 2);
        aquario.posicionarPeixe(0, 0, new PeixeB());
        aquario.posicionarPeixe(0, 1, new PeixeA());
        aquario.posicionarPeixe(0, 2, new PeixeA());

        aquario.moverPeixe(0, 0, 0, 1);
        aquario.moverPeixe(0, 1, 0, 2);
        aquario.moverPeixe(0, 2, 1, 2);

        assertNull(aquario.getPeixe(0, 0));
        assertNull(aquario.getPeixe(0, 1));
        assertNotNull(aquario.getPeixe(0, 2));
        assertNotNull(aquario.getPeixe(1, 2));
    }

    @Test
    public void testMortalidadePeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 1, 1, 2);
        aquario.posicionarPeixe(0, 0, new PeixeB());
        assertNotNull(aquario.getPeixe(0, 0)); // parado 0

        aquario.forcarProximaIteracao();
        assertNotNull(aquario.getPeixe(0, 0)); // parado 1

        aquario.forcarProximaIteracao();
        assertNull(aquario.getPeixe(0, 0)); // parado 2
    }

    // endregion

    // region Encerramento e Pontuação

    @Test
    public void testEncerramentoPorExtincaoDosPeixesB() {
        Game game = new Game();
        game.newAquario(1, 1, 0, 1, 1, 1, 1, 1);

        String saida = capturarSaida(() -> {
            game.skipCycle();
            game.endGame();
        });

        assertTrue(saida.contains("Fim do jogo."));
        assertTrue(saida.contains("Total de turnos: 1"));
    }

    @Test
    public void testEncerramentoPorAcaoDoJogador() {
        Game game = new Game();
        Aquario aquario = new Aquario(1, 1, 0, 1, 1, 1, 1, 3);
        game.newAquario(aquario);

        String saida = capturarSaida(() -> {
            game.nextCycle();
            aquario.removerPeixe(0, 0);
            game.endGame();
        });

        assertTrue(saida.contains("Fim do jogo."));
        assertTrue(saida.contains("Total de turnos: 1"));
    }

    @Test
    public void testContinuidadeDoEcossistema() {
        Game game = new Game();
        game.newAquario(1, 1, 0, 1, 1, 1, 1, 3);

        String saida = capturarSaida(() -> {
            game.nextCycle();
            game.nextCycle();
        });

        assertTrue(saida.contains("Turno: 1"));
        assertTrue(saida.contains("Turno: 2"));
    }

    private String capturarSaida(Runnable acao) {
        PrintStream saidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            acao.run();
            return buffer.toString();
        } finally {
            System.setOut(saidaOriginal);
        }
    }

    // endregion

}
