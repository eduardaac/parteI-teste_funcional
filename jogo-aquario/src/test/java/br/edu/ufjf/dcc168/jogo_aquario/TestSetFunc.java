package br.edu.ufjf.dcc168.jogo_aquario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSetFunc {

    //region Validação de Inicialização

    @Test
    public void testAquarioValido() {
        Aquario aquario = new Aquario(1, 2, 1, 1, 1, 1, 1, 1);
        assertNotNull(aquario);
        assertEquals(1, aquario.getLinhas());
        assertEquals(2, aquario.getColunas());
        assertEquals(1, aquario.getReproducaoA());
        assertEquals(1, aquario.getMortalidadeA());
        assertEquals(1, aquario.getReproducaoB());
        assertEquals(1, aquario.getMortalidadeB());
    }

    @Test
    public void testAquarioDimensaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Aquario(0, 5, 1, 1, 2, 2, 2, 2));
    }

    @Test 
    public void testPeixesEmExcesso() {
        assertThrows(IllegalArgumentException.class, () -> new Aquario(2, 2, 3, 2, 2, 2, 2, 2));
    }

    @Test 
    public void testAquarioParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Aquario(5, 5, 2, 2, 0, 2, 2, 2));
    }

    //endregion

    //region Regras e Ciclo de Vida Peixe A

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

    //endregion

    //region Regras e Ciclo de Vida Peixe B

    @Test 
    public void testAlimentacaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 1, 1, 1, 1);
        aquario.posicionarPeixe(1, 1, new PeixeB());
        assertNotNull(aquario.getPeixe(1, 1));
        
        aquario.posicionarPeixe(1, 2, new PeixeA());
        assertNotNull(aquario.getPeixe(1, 2));
        
        aquario.moverPeixe(1, 1, 1, 2);
        assertNull(aquario.getPeixe(1, 1));
        
        assertNotNull(aquario.getPeixe(1, 2));
        assertEquals(aquario.getPeixe(1, 2).getTipo(), Tipo.B);
    }

    @Test 
    public void testMovimentacaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 2, 2, 2, 2);
        aquario.posicionarPeixe(1, 1, new PeixeB());

        assertTrue(aquario.moverPeixe(1, 1, 1, 2));

        assertNull(aquario.getPeixe(1, 1));
        assertNotNull(aquario.getPeixe(1, 2));
        assertEquals(aquario.getPeixe(1, 2).getFome(), -1);
    }

    @Test
    public void testReproducaoPeixeB() {
        Aquario aquario = new Aquario(3, 3, 0, 0, 2, 2, 2, 2);
        aquario.posicionarPeixe(0, 0, new PeixeB());
        aquario.posicionarPeixe(0, 1, new PeixeA());
        aquario.posicionarPeixe(0,2, new PeixeA());
        
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

    //endregion

    //region Encerramento e Pontuação

    //TODO: implementar esses testes depois de ter implementado a parte de encerramento e pontuação

    //endregion
}
//TODO: modificar nos testes: new Aquario -> Game.newAquario