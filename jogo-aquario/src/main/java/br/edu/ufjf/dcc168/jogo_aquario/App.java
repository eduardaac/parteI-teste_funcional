package br.edu.ufjf.dcc168.jogo_aquario;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Jogo do Aquário!");
        System.out.println("1. Iniciar novo aquário");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();
        if (opcao == 1) {
            System.out.print("Digite o número de linhas do aquário: ");
            int linhas = scanner.nextInt();
            System.out.print("Digite o número de colunas do aquário: ");
            int colunas = scanner.nextInt();
            System.out.print("Digite o número de peixes do tipo A: ");
            int peixesA = scanner.nextInt();
            System.out.print("Digite o número de peixes do tipo B: ");
            int peixesB = scanner.nextInt();
            System.out.print("Digite o número de ciclos para reprodução do tipo A: ");
            int reproducaoA = scanner.nextInt();
            System.out.print("Digite o número de ciclos para mortalidade do tipo A: ");
            int mortalidadeA = scanner.nextInt();
            System.out.print("Digite o número de ciclos para reprodução do tipo B: ");
            int reproducaoB = scanner.nextInt();
            System.out.print("Digite o número de ciclos para mortalidade do tipo B: ");
            int mortalidadeB = scanner.nextInt();
            System.out.println("Criando aquário...");
            Game game = new Game();
            game.newAquario(linhas, colunas, peixesA, peixesB, reproducaoA, mortalidadeA, reproducaoB, mortalidadeB);
            System.out.println("Aquário criado com sucesso!");
            game.nextCycle();
            do {
                System.out.println("1. Próximo ciclo");
                System.out.println("2. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                if (opcao == 1) {
                    game.nextCycle();
                } else if (opcao == 2) {
                    System.out.println("Saindo do jogo...");
                    game.endGame();
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 2);
        } else if (opcao == 2) {
            System.out.println("Saindo do jogo...");
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
        scanner.close();
    }
}
