package principal;

import utils.Config;
import utils.SplashScreen;
import java.util.Scanner;
import reports.ImprimirRelatorios;
import controller.*;

public class Main {

    private static EspecialidadeController especialidadeController = new EspecialidadeController();
    private static HospitalController hospitalController = new HospitalController();
    private static PacienteController pacienteController = new PacienteController();
    private static MedicoController medicoController = new MedicoController();
    private static HistoricoController historicoController = new HistoricoController();
    private static HospitalMedicoController hospitalMedicoController = new HospitalMedicoController();
    private static EspecialidadeMedicoController especialidadeMedicoController = new EspecialidadeMedicoController();
    private static ImprimirRelatorios relatorios = new ImprimirRelatorios();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();

        while (true) {
            System.out.println(splashScreen.obterTelaAtualizada());
            System.out.println(Config.MENU_PRINCIPAL);
            System.out.print("Escolha uma opcao [1-5]: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcao) {
                    case 1:
                        exibirMenuRelatorios();
                        break;
                    case 2:
                        exibirMenuInserir();
                        break;
                    case 3:
                        exibirMenuAtualizar();
                        break;
                    case 4:
                        exibirMenuRemover();
                        break;
                    case 5:
                        System.out.println("Obrigado por utilizar o sistema.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero.");
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
                    case 1 -> relatorios.imprimirRelatorioEspecialidades();
                    case 2 -> relatorios.imprimirRelatorioHospitais();
                    case 3 -> relatorios.imprimirRelatorioPacientes();
                    case 4 -> relatorios.imprimirRelatorioMedicos();
                    case 5 -> relatorios.imprimirRelatorioHistorico();
                    case 6 -> relatorios.imprimirRelatorioEspecialidadeMedicos();
                    case 7 -> relatorios.imprimirRelatorioHospitalMedicos();
                    case 8 -> relatorios.imprimirRelatorioAgrupamentoEsp();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero.");
            }
        }
    }

    private static void exibirMenuInserir() {
        while (true) {
            System.out.println(Config.MENU_ENTIDADES);
            System.out.print("Escolha uma opcao [0-7]: ");

            try {
                int opcaoInserir = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcaoInserir) {
                    case 1 -> especialidadeController.cadastrarEspecialidade();
                    case 2 -> hospitalController.cadastrarHospital();
                    case 3 -> pacienteController.cadastrarPaciente();
                    case 4 -> medicoController.cadastrarMedico();
                    case 5 -> historicoController.cadastrarHistorico();
                    case 6 -> especialidadeMedicoController.cadastrarEspecialidadeXMedico();
                    case 7 -> hospitalMedicoController.cadastrarMedicoXHospital();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default -> {
                        System.out.println("Opcao invalida.");
                        continue;
                    }
                }

                while (true) {
                    System.out.print("Deseja inserir mais algum registro? (Sim/Nao): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();

                    if (resposta.equals("nao")) {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    } else if (resposta.equals("sim")) {
                        break;
                    } else {
                        System.out.println("Resposta invalida. Por favor, digite 'Sim' ou 'Nao'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero.");
            }
        }
    }

    private static void exibirMenuAtualizar() {
        while (true) {
            System.out.println(Config.MENU_ENTIDADES);
            System.out.print("Escolha uma opcao [0-7]: ");

            try {
                int opcaoAtualizar = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcaoAtualizar) {
                    case 1 -> especialidadeController.atualizarEspecialidade();
                    case 2 -> hospitalController.atualizarHospital();
                    case 3 -> pacienteController.atualizarPaciente();
                    case 4 -> medicoController.atualizarMedico();
                    case 5 -> historicoController.atualizarHistorico();
                    case 6 -> especialidadeMedicoController.atualizarEspecialidadeXMedico();
                    case 7 -> hospitalMedicoController.atualizarMedicoXHospital();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default -> {
                        System.out.println("Opcao invalida.");
                        continue;
                    }
                }

                while (true) {
                    System.out.print("Deseja atualizar mais algum registro? (Sim/Nao): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();

                    if (resposta.equals("nao")) {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    } else if (resposta.equals("sim")) {
                        break;
                    } else {
                        System.out.println("Resposta invalida. Por favor, digite 'Sim' ou 'Nao'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero.");
            }
        }
    }

    private static void exibirMenuRemover() {
        while (true) {
            System.out.println(Config.MENU_ENTIDADES);
            System.out.print("Escolha uma opcao [0-7]: ");

            try {
                int opcaoRemover = Integer.parseInt(scanner.nextLine());
                Config.limparConsole(1);

                switch (opcaoRemover) {
                    case 1 -> especialidadeController.deletarEspecialidade();
                    case 2 -> hospitalController.deletarHospital();
                    case 3 -> pacienteController.deletarPaciente();
                    case 4 -> medicoController.deletarMedico();
                    case 5 -> historicoController.deletarHistorico();
                    case 6 -> especialidadeMedicoController.deletarEspecialidadeXMedico();
                    case 7 -> hospitalMedicoController.deletarMedicoXHospital();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default -> {
                        System.out.println("Opcao invalida.");
                        continue;
                    }
                }

                while (true) {
                    System.out.print("Deseja remover mais algum registro? (Sim/Nao): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();

                    if (resposta.equals("nao")) {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    } else if (resposta.equals("sim")) {
                        break;
                    } else {
                        System.out.println("Resposta invalida. Por favor, digite 'Sim' ou 'Nao'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero.");
            }
        }
    }
}