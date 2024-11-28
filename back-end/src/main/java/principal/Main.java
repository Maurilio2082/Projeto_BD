package principal;

import utils.Config;
import utils.SplashScreen;
import java.util.Scanner;
import reports.ImprimirRelatorios;

import controller.*;

public class Main {

    private static final EspecialidadeController especialidadeController = new EspecialidadeController();
    private static final HospitalController hospitalController = new HospitalController();
    private static final PacienteController pacienteController = new PacienteController();
    private static final MedicoController medicoController = new MedicoController();
    private static final HistoricoController historicoController = new HistoricoController();
    private static final HospitalMedicoController hospitalMedicoController = new HospitalMedicoController();
    private static final EspecialidadeMedicoController especialidadeMedicoController = new EspecialidadeMedicoController();
    private static final ImprimirRelatorios relatorios = new ImprimirRelatorios();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();

        while (true) {
            System.out.println(splashScreen.obterTelaAtualizada());
            System.out.println(Config.MENU_PRINCIPAL);
            System.out.print("Escolha uma opção [1-5]: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcao) {
                    case 1 -> exibirMenuRelatorios();
                    case 2 -> exibirMenuInserir();
                    case 3 -> exibirMenuAtualizar();
                    case 4 -> exibirMenuRemover();
                    case 5 -> {
                        System.out.println("Obrigado por utilizar o sistema. Encerrando...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }

            Config.limparConsole(1);
        }
    }

    private static void exibirMenuRelatorios() {
        while (true) {
            System.out.println(Config.MENU_RELATORIOS);
            System.out.print("Escolha uma opção [0-8]: ");

            try {
                int opcaoRelatorio = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcaoRelatorio) {
                    case 1 -> relatorios.imprimirRelatorioEspecialidades(true);
                    case 2 -> relatorios.imprimirRelatorioHospitais(true);
                    case 3 -> relatorios.imprimirRelatorioPacientes(true);
                    case 4 -> relatorios.imprimirRelatorioMedicos(true);
                    case 5 -> relatorios.imprimirRelatorioHistorico(true);
                    case 6 -> relatorios.imprimirRelatorioEspecialidadeMedicos(true);
                    case 7 -> relatorios.imprimirRelatorioHospitalMedicos(true);
                    case 8 -> relatorios.imprimirRelatorioAgrupamentoEsp(true);
                    case 9 -> relatorios.imprimirTodosRelatorios();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
    }

    private static void exibirMenuInserir() {
        executarMenu(Config.MENU_ENTIDADES, "inserir", (opcaoInserir) -> {
            switch (opcaoInserir) {
                case 1 -> especialidadeController.cadastrarEspecialidade();
                case 2 -> hospitalController.cadastrarHospital();
                case 3 -> pacienteController.cadastrarPaciente();
                case 4 -> medicoController.cadastrarMedico();
                case 5 -> historicoController.cadastrarHistorico();
                case 6 -> especialidadeMedicoController.cadastrarEspecialidadeXMedico();
                case 7 -> hospitalMedicoController.cadastrarMedicoXHospital();
                default -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void exibirMenuAtualizar() {
        executarMenu(Config.MENU_ENTIDADES, "atualizar", (opcaoAtualizar) -> {
            switch (opcaoAtualizar) {
                case 1 -> especialidadeController.atualizarEspecialidade();
                case 2 -> hospitalController.atualizarHospital();
                case 3 -> pacienteController.atualizarPaciente();
                case 4 -> medicoController.atualizarMedico();
                case 5 -> historicoController.atualizarHistorico();
                case 6 -> especialidadeMedicoController.atualizarEspecialidadeXMedico();
                case 7 -> hospitalMedicoController.atualizarMedicoXHospital();
                default -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void exibirMenuRemover() {
        executarMenu(Config.MENU_ENTIDADES, "remover", (opcaoRemover) -> {
            switch (opcaoRemover) {
                case 1 -> especialidadeController.deletarEspecialidade();
                case 2 -> hospitalController.deletarHospital();
                case 3 -> pacienteController.deletarPaciente();
                case 4 -> medicoController.deletarMedico();
                case 5 -> historicoController.deletarHistorico();
                case 6 -> especialidadeMedicoController.deletarEspecialidadeXMedico();
                case 7 -> hospitalMedicoController.deletarMedicoXHospital();
                default -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void executarMenu(String menu, String acao, java.util.function.Consumer<Integer> action) {
        while (true) {
            System.out.println(menu);
            System.out.printf("Escolha uma opção [0-7] para %s ou 0 para voltar: ", acao);

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                if (opcao == 0) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }

                action.accept(opcao);

                while (true) {
                    System.out.print("Deseja realizar mais alguma operação? (Sim/Não): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();

                    if (resposta.equals("não") || resposta.equals("nao")) {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    } else if (resposta.equals("sim")) {
                        break;
                    } else {
                        System.out.println("Resposta inválida. Por favor, digite 'Sim' ou 'Não'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
    }
}
