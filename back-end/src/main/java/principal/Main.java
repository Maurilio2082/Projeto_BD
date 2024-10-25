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
            int opcao = scanner.nextInt();
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
            Config.limparConsole(1);
        }
    }

    private static void exibirMenuRelatorios() {
        System.out.println(Config.MENU_RELATORIOS);
        System.out.print("Escolha uma opção [0-7]: ");
        int opcaoRelatorio = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoRelatorio) {
            case 1 -> relatorios.imprimirRelatorioEspecialidades();
            case 2 -> relatorios.imprimirRelatorioHospitais();
            case 3 -> relatorios.imprimirRelatorioPacientes();
            case 4 -> relatorios.imprimirRelatorioMedicos();
            case 5 -> relatorios.imprimirRelatorioHistorico();
            case 6 -> relatorios.imprimirRelatorioEspecialidadeMedicos();
            case 7 -> relatorios.imprimirRelatorioHospitalMedicos();
            case 0 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opcao invalida.");
        }
    }

    private static void exibirMenuInserir() {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opcao [1-7]: ");
        int opcaoInserir = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoInserir) {
            case 1 -> especialidadeController.cadastrarEspecialidade();
            case 2 -> hospitalController.cadastrarHospital();
            case 3 -> pacienteController.cadastrarPaciente();
            case 4 -> medicoController.cadastrarMedico();
            case 5 -> historicoController.cadastrarHistorico();
            case 6 -> especialidadeMedicoController.cadastrarEspecialidadeXMedico();
            case 7 -> hospitalMedicoController.cadastrarMedicoXHospital();
            default -> System.out.println("Opcao invalida.");
        }
    }

    private static void exibirMenuAtualizar() {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opcao [1-7]: ");
        int opcaoAtualizar = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoAtualizar) {
            case 1 -> especialidadeController.atualizarEspecialidade();
            case 2 -> hospitalController.atualizarHospital();
            case 3 -> pacienteController.atualizarPaciente();
            case 4 -> medicoController.atualizarMedico();
            case 5 -> historicoController.atualizarHistorico();
            case 6 -> especialidadeMedicoController.atualizarEspecialidadeXMedico();
            case 7 -> hospitalMedicoController.atualizarMedicoXHospital();
            default -> System.out.println("Opcao invalida.");
        }
    }

    private static void exibirMenuRemover() {
        System.out.println(Config.MENU_ENTIDADES);
        System.out.print("Escolha uma opcao [1-7]: ");
        int opcaoRemover = scanner.nextInt();
        Config.limparConsole(1);

        switch (opcaoRemover) {
            case 1 -> especialidadeController.deletarEspecialidade();
            case 2 -> hospitalController.deletarHospital();
            case 3 -> pacienteController.deletarPaciente();
            case 4 -> medicoController.deletarMedico();
            case 5 -> historicoController.deletarHistorico();
            case 6 -> especialidadeMedicoController.deletarEspecialidadeXMedico();
            case 7 -> hospitalMedicoController.deletarMedicoXHospital();
            default -> System.out.println("Opcao invalida.");
        }
    }
}
