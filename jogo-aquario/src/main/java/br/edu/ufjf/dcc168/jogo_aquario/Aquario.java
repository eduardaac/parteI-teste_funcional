package br.edu.ufjf.dcc168.jogo_aquario;

public class Aquario {

    private int linhas;
    private int colunas;
    private IPeixe[][] matriz;
    private int reproducaoA;
    private int mortalidadeA;
    private int reproducaoB;
    private int mortalidadeB;

    public Aquario(int m, int n, int x, int y, int rA, int mA, int rB, int mB) {
        if (m <= 0 || n <= 0 || x < 0 || y < 0 || rA <= 0 || mA <= 0 || rB <= 0 || mB <= 0)
            throw new IllegalArgumentException("As dimensoes devem ser positivas e as quantidades e taxas devem ser não-negativas.");

        if (x + y > m * n)
            throw new IllegalArgumentException("A quantidade total de peixes não pode exceder o número de células do aquário.");

        this.linhas = m;
        this.colunas = n;
        this.matriz = new IPeixe[m][n];
        this.reproducaoA = rA;
        this.mortalidadeA = mA;
        this.reproducaoB = rB;
        this.mortalidadeB = mB;

        for(int i = 0; i < x; i++) {
            int linha, coluna;
            IPeixe peixe = new PeixeA();
            do {
                linha = (int) (Math.random() * m);
                coluna = (int) (Math.random() * n);
            } while(!posicionarPeixe(linha, coluna, peixe));
        }

        for(int i = 0; i < y; i++) {
            int linha, coluna;
            IPeixe peixe = new PeixeB();
            do {
                linha = (int) (Math.random() * m);
                coluna = (int) (Math.random() * n);
            } while(!posicionarPeixe(linha, coluna, peixe));
        }

    }

    public int getLinhas() { return linhas; }

    public int getColunas() { return colunas; }

    public int getReproducaoA() { return reproducaoA; }

    public int getMortalidadeA() { return mortalidadeA; }

    public int getReproducaoB() { return reproducaoB; }

    public int getMortalidadeB() { return mortalidadeB; }

    public IPeixe getPeixe(int linha, int coluna) {
        if(!coordenadaValida(linha, coluna)) return null;
        return matriz[linha][coluna];
    }

    public IPeixe removerPeixe(int linha, int coluna) {
        if(!coordenadaValida(linha, coluna)) return null;
        IPeixe peixe = matriz[linha][coluna];
        matriz[linha][coluna] = null;
        return peixe;
    }

    public boolean coordenadaValida(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

	public boolean posicionarPeixe(int linha, int coluna, IPeixe peixe) {
	    if (peixe == null) return false;
	    if (!coordenadaValida(linha, coluna)) return false;
	    if (matriz[linha][coluna] != null)  return false;
	
	    matriz[linha][coluna] = peixe;
	    return true;
	}

    public boolean moverPeixe(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        IPeixe p = getPeixe(linhaOrigem, colunaOrigem);

        if(p == null) return false;

        if(!coordenadaValida(linhaDestino, colunaDestino)) return false;

        if(p.getTipo() == Tipo.A)
            return acaoA(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, p);

        else if(p.getTipo() == Tipo.B)
            return acaoB(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, p);

        return false;
    }

    private boolean acaoA(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, IPeixe p) {
        if(matriz[linhaDestino][colunaDestino] != null) return false;

        if(p.getReproducao() >= reproducaoA) {
            p.resetarReproducao();
            return posicionarPeixe(linhaDestino, colunaDestino, new PeixeA());
        }
        else
            return moverPeixeA(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, p);
    }

    private boolean moverPeixeA(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, IPeixe p) {
        matriz[linhaDestino][colunaDestino] = p;
        matriz[linhaOrigem][colunaOrigem] = null;
        p.incrementarFome();
        p.incrementarReproducao();
        return true;
    }

    private boolean acaoB(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, IPeixe p) {
        if(matriz[linhaDestino][colunaDestino] != null) {
            IPeixe p2 = getPeixe(linhaDestino, colunaDestino);
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
            if (p.getReproducao() >= reproducaoB) {
                p.resetarReproducao();
                return posicionarPeixe(linhaDestino, colunaDestino, new PeixeB());
            }
            return moverPeixeB(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, p);
        }
    }

    private boolean moverPeixeB(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, IPeixe p) {
        matriz[linhaDestino][colunaDestino] = p;
        matriz[linhaOrigem][colunaOrigem] = null;
        p.decrementarFome();
        return true;
    }

    public void avancarCiclo() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                IPeixe peixe = matriz[i][j];
                if (peixe != null) {
                    peixe.decrementarFome();
                    if (peixe.getTipo() == Tipo.A && -(peixe.getFome()) >= getMortalidadeA()) {
                        removerPeixe(i, j);
                    }
                    if (peixe.getTipo() == Tipo.B && -(peixe.getFome()) >= getMortalidadeB()) {
                        removerPeixe(i, j);
                    }
                }
            }
        }
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
