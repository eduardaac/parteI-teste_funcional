package br.edu.ufjf.dcc168.jogo_aquario;

public class Peixe {

    public enum Tipo {
        A, B
    }

    private final Tipo tipo;

    private int movimentosConsecutivos;
    private int iteracoesSemMovimento;
    private int peixesComidos;
    private int iteracoesSemComer;

	public Peixe(Tipo tipo) {
	    if (tipo == null) {
	        throw new IllegalArgumentException(
	            "O tipo do peixe deve ser A ou B."
	        );
	    }
	
	    this.tipo = tipo;
	}

    public Tipo getTipo() {
        return tipo;
    }

    public int getMovimentosConsecutivos() {
        return movimentosConsecutivos;
    }

    public void registrarMovimento() {
        movimentosConsecutivos++;
        iteracoesSemMovimento = 0;
    }

    public void registrarSemMovimento() {
        movimentosConsecutivos = 0;
        iteracoesSemMovimento++;
    }

    public int getIteracoesSemMovimento() {
        return iteracoesSemMovimento;
    }

    public int getPeixesComidos() {
        return peixesComidos;
    }

    public void registrarPeixeComido() {
        peixesComidos++;
        iteracoesSemComer = 0;
    }

    public void registrarSemComer() {
        iteracoesSemComer++;
    }

    public int getIteracoesSemComer() {
        return iteracoesSemComer;
    }
}
