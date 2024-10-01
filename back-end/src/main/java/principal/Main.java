package principal;

import utils.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SplashScreen telaInicial = new SplashScreen();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(telaInicial.obterTelaAtualizada());

            // Usando o menu da classe Config
            System.out.println(Config.MENU_PRINCIPAL);
            System.out.print("Escolha uma opção [1-5]: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> menuRelatorios();
                case 2 -> inserirRegistros();
                case 3 -> alterarRegistros();
                case 4 -> removerRegistros();
                case 5 -> {
                    System.out.println("Obrigado por utilizar o nosso sistema.");
                    return;
                }
                default -> System.out.println("Opção incorreta.");
            }

        }

    }

    private static void menuRelatorios() {
        // Usando o menu de relatórios da classe Config
        System.out.println(Config.MENU_RELATORIOS);
    }

    private static void inserirRegistros() {
        // Usando o menu de entidades da classe Config
        System.out.println(Config.MENU_ENTIDADES);
    }

    private static void alterarRegistros() {
        // Usando o menu de entidades da classe Config
        System.out.println(Config.MENU_ENTIDADES);
    }

    private static void removerRegistros() {
        // Usando o menu de entidades da classe Config
        System.out.println(Config.MENU_ENTIDADES);
    }
}
