package principal;

import utils.Config;
import utils.SplashScreen;
import java.util.Scanner;
import model.*;
import reports.Relatorios;
import controller.*;

public class Main {

    private static EspecialidadeController especialidadeController = new EspecialidadeController();
    private static HospitalController hospitalController = new HospitalController();
    private static PacienteController pacienteController = new PacienteController();
    private static MedicoController medicoController = new MedicoController();
    private static HistoricoController historicoController = new HistoricoController();
    private static Relatorios relatorios = new Relatorios();

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();
        Scanner scanner = new Scanner(System.in);

        System.out.println(splashScreen.obterTelaAtualizada());
        Config.limparConsole(1);

        while (true) {
            System.out.println(Config.MENU_PRINCIPAL);
            System.out.print("Escolha uma opção [1-5]: ");
            int opcao = scanner.nextInt();
            Config.limparConsole(1);

            switch (opcao) {
                case 1:
                    exibirMenuRelatorios(scanner);
                    break;
                case 2:
                    exibirMenuInserir(scanner);
                    break;
                case 3:
                    exibirMenuAtualizar(scanner);
                    break;
                case 4:
                    exibirMenuRemover(scanner);
                    break;
                case 5:
                    System.out.println("Obrigado por utilizar o sistema.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            Config.limparConsole(1);
        }
    }

    private static void exibirMenuRelatorios(Scanner scanner) {
        System.out.println(Config.MENU_RELATORIOS);
        System.out.print("Escolha uma opção [0-7]: ");
        int opcaoRelatorio = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoRelatorio) {
            case 1 -> relatorios.getRelatorioEspecialidades();
            case 2 -> relatorios.getRelatorioHospitais();
            case 3 -> relatorios.getRelatorioPacientes();
            case 4 -> relatorios.getRelatorioMedicos();
            case 5 -> relatorios.getRelatorioHistoricos();
            case 6 -> relatorios.getRelatorioEspecialidadeXMedicos();
            case 7 -> relatorios.getRelatorioMedicosXHospitais();
            case 0 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void exibirMenuInserir(Scanner scanner) {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opção [1-5]: ");
        int opcaoInserir = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoInserir) {
            case 1 -> especialidadeController.cadastrarEspecialidade();
            case 2 -> hospitalController.cadastrarHospital();
            case 3 -> pacienteController.cadastrarPaciente();
            case 4 -> medicoController.cadastrarMedico();
            case 5 -> historicoController.cadastrarHistorico();
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void exibirMenuAtualizar(Scanner scanner) {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opção [1-5]: ");
        int opcaoAtualizar = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoAtualizar) {
            case 1 -> especialidadeController.atualizarEspecialidade();
            case 2 -> hospitalController.atualizarHospital();
            case 3 -> pacienteController.atualizarPaciente();
            case 4 -> medicoController.atualizarMedico();
            case 5 -> historicoController.alterarHistorico();
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void exibirMenuRemover(Scanner scanner) {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opção [1-5]: ");
        int opcaoRemover = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoRemover) {
            case 1 -> especialidadeController.deletarEspecialidade();
            case 2 -> hospitalController.deletarHospital();
            case 3 -> pacienteController.deletarHemocentro();
            case 4 -> medicoController.deletarMedico();
            case 5 -> historicoController.deletarHistorico();
            case 6 -> especialidadeController.deletarEspecialidade();
            default -> System.out.println("Opção inválida.");
        }
    }
}