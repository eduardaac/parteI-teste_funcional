package br.edu.ufjf.dcc168.jogo_aquario;

public class Aquario {

    private int linhas;
    private int colunas;
    private IPeixe[][] matriz;

    public Aquario(int linhas, int colunas) {
        if (linhas <= 0 || colunas <= 0) {
            throw new IllegalArgumentException(
                "As dimensoes devem ser positivas."
            );
        }

        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new IPeixe[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }


	public boolean posicionarPeixe(int linha, int coluna, IPeixe peixe) {

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

    public boolean moverPeixe(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        // Verifica se as coordenadas de origem e destino são válidas
        if(linhaOrigem < 0 || linhaOrigem >= linhas || colunaOrigem < 0 || colunaOrigem >= colunas || linhaDestino < 0 || linhaDestino >= linhas || colunaDestino < 0 || colunaDestino >= colunas)
            return false;

        // Verifica se há um peixe na posição de origem
        if(matriz[linhaOrigem][colunaOrigem] == null) return false;

        IPeixe p = matriz[linhaOrigem][colunaOrigem];
        
        if(p.getTipo() == Tipo.A) {
            if(matriz[linhaDestino][colunaDestino] != null) return false;
            else {
                matriz[linhaDestino][colunaDestino] = p;
                matriz[linhaOrigem][colunaOrigem] = null;
                p.incrementarFome();
                p.incrementarReproducao();
                return true;
            }
        }
        
        else if(p.getTipo() == Tipo.B) {
            if(matriz[linhaDestino][colunaDestino] != null) {
                IPeixe p2 = matriz[linhaDestino][colunaDestino];
                if(p2.getTipo() == Tipo.A) {
                    matriz[linhaDestino][colunaDestino] = p;
                    matriz[linhaOrigem][colunaOrigem] = null;
                    p.incrementarFome();
                    p.incrementarReproducao();
                    return true;
                }
                else return false;
            }
            else {
                matriz[linhaDestino][colunaDestino] = p;
                matriz[linhaOrigem][colunaOrigem] = null;
                p.decrementarFome();
                return true;
            }
        }
        return false;
    }

    public void exibir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                IPeixe peixe = matriz[i][j];

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
