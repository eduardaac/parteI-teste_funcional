package br.edu.ufjf.dcc168.jogo_aquario;

import java.util.ArrayList;
import java.util.List;

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

    public boolean coordenadaValida(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean coordenadaOcupada(int linha, int coluna) {
        if(!coordenadaValida(linha, coluna)) return false;
        return matriz[linha][coluna] != null;
    }

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

	public boolean posicionarPeixe(int linha, int coluna, IPeixe peixe) {
	    if (peixe == null) return false;
	    if (!coordenadaValida(linha, coluna)) return false;
	    if (coordenadaOcupada(linha, coluna)) return false;

	    peixe.registrarMovimento();
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
        if(coordenadaOcupada(linhaDestino, colunaDestino)) return false;

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
        if(coordenadaValida(linhaDestino, colunaDestino) && coordenadaOcupada(linhaDestino, colunaDestino)) {
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

    private int[][] buscarCasasAdjacentes(int linha, int coluna, Tipo tipo) {
        List<int[]> casasAdjacentes = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int novaLinha = linha + i;
                int novaColuna = coluna + j;
                if (coordenadaValida(novaLinha, novaColuna)) {
                    IPeixe peixeAdjacente = getPeixe(novaLinha, novaColuna);
                    switch(tipo) {
                        case A:
                            if (peixeAdjacente != null && peixeAdjacente.getTipo() == Tipo.A) {
                                casasAdjacentes.add(new int[]{novaLinha, novaColuna});
                            }
                            break;
                        case B:
                            if (peixeAdjacente != null && peixeAdjacente.getTipo() == Tipo.B) {
                                casasAdjacentes.add(new int[]{novaLinha, novaColuna});
                            }
                            break;
                        case N:
                            if (peixeAdjacente == null) {
                                casasAdjacentes.add(new int[]{novaLinha, novaColuna});
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if (casasAdjacentes.isEmpty()) {
            return null;
        }
        return casasAdjacentes.toArray(new int[casasAdjacentes.size()][]);
    }

    public void iterarCiclo() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (getPeixe(i,j) == null) continue;
                if (getPeixe(i,j).realizouMovimentoNoTurno()) continue;
                if (getPeixe(i,j).getTipo() == Tipo.A)
                    escolhaDeAcaoPeixeA(i, j, getPeixe(i,j));
                else if (getPeixe(i,j).getTipo() == Tipo.B)
                    escolhaDeAcaoPeixeB(i, j, getPeixe(i,j));
            }
        }

        proximaIteracao();
    }

    private void proximaIteracao() {
        for(int i=0;i<linhas;i++)
            for(int j=0;j<colunas;j++)
                if(getPeixe(i,j) != null) {
                    getPeixe(i,j).resetarMovimento();
                    if (getPeixe(i,j).getTipo() == Tipo.A) {
                        if (-getPeixe(i,j).getFome() >= getMortalidadeA())
                            removerPeixe(i, j);
                    }
                    else if (getPeixe(i,j).getTipo() == Tipo.B) {
                        if (-getPeixe(i,j).getFome() >= getMortalidadeB())
                            removerPeixe(i, j);
                    }
                }
    }

    private void escolhaDeAcaoPeixeB(int i, int j, IPeixe peixe) {
        int[][] adjacentes = buscarCasasAdjacentes(i, j, Tipo.A);
        if (adjacentes != null) {
            int[]destino = adjacentes[(int) (Math.random() * adjacentes.length)];
            if (moverPeixe(i, j, destino[0], destino[1]))
                peixe.registrarMovimento();
        }
        else {
            adjacentes = buscarCasasAdjacentes(i, j, Tipo.N);
            if (adjacentes != null) {
                int[]destino = adjacentes[(int) (Math.random() * adjacentes.length)];
                if (moverPeixe(i, j, destino[0], destino[1]))
                    peixe.registrarMovimento();
            }
            else {
                peixe.decrementarFome();
                peixe.registrarMovimento();
            }
        }
        
    }

    private void escolhaDeAcaoPeixeA(int i, int j, IPeixe peixe) {
        int[][] adjacentes = buscarCasasAdjacentes(i, j, Tipo.N);
        if (adjacentes != null) {
            int[]destino = adjacentes[(int) (Math.random() * adjacentes.length)];
            if (moverPeixe(i, j, destino[0], destino[1]))
                peixe.registrarMovimento();
        }
        else {
            peixe.decrementarFome();
            peixe.registrarMovimento();
        }
    }

    public void forcarProximaIteracao() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (getPeixe(i,j) != null) {
                    getPeixe(i,j).decrementarFome();
                    if (getPeixe(i,j).getTipo() == Tipo.A) {
                        if (-(getPeixe(i,j).getFome()) >= getMortalidadeA())
                            removerPeixe(i, j);
                    }
                    else if (getPeixe(i,j).getTipo() == Tipo.B) {
                        if (-(getPeixe(i,j).getFome()) >= getMortalidadeB())
                            removerPeixe(i, j);
                    }
                }
            }
        }
    }

    //TODO: mover exibir() para Game.java
    public void exibir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (getPeixe(i,j) == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(getPeixe(i,j).getTipo() + " ");
                }
            }
            System.out.println();
        }
    }

    public int contarPeixes(Tipo tipo) {
        int count = 0;
        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < colunas; j++)
                if (coordenadaOcupada(i, j))
                    if (getPeixe(i, j).getTipo() == tipo)
                        count++;
        return count;
    }

}
