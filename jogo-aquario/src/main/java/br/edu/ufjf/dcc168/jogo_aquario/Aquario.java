package br.edu.ufjf.dcc168.jogo_aquario;

public class Aquario {

    private int linhas;
    private int colunas;
    private Peixe[][] matriz;

    public Aquario(int linhas, int colunas) {
        if (linhas <= 0 || colunas <= 0) {
            throw new IllegalArgumentException(
                "As dimensoes devem ser positivas."
            );
        }

        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new Peixe[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }


	public boolean posicionarPeixe(
	        int linha, int coluna, Peixe peixe) {
	
	    if (peixe == null) {
	        return false;
	    }
	
	    if (linha < 0 || linha >= linhas
	            || coluna < 0 || coluna >= colunas) {
	        return false;
	    }
	
	    if (matriz[linha][coluna] != null) {
	        return false;
	    }
	
	    matriz[linha][coluna] = peixe;
	    return true;
	}

    public void exibir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peixe peixe = matriz[i][j];

                if (peixe == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(peixe.getTipo() + " ");
                }
            }

            System.out.println();
        }
    }
}
