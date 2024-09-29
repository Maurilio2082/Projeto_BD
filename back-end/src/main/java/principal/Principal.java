package principal;

import controller.EspecialidadeController;
import model.Especialidade;
import utils.MenuUtils;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EspecialidadeController controller = new EspecialidadeController();
        boolean running = true;

        while (running) {
            MenuUtils.mostrarMenu();
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:

                    controller.listarProdutos().forEach(
                            especialidade -> System.out.println("Código: " + especialidade.getIdEspecialidade() +
                                    ", Nome: " + especialidade.getNomeEspecialidade()));
                    break;
                case 2:
                    // Buscar produto por código
                    System.out.print("Digite o código da especialidade: ");
                    int codigo = scanner.nextInt();
                    Especialidade especialidade = controller.buscarPorCodigo(codigo);
                    if (especialidade != null) {
                        System.out.println("Código: " + especialidade.getIdEspecialidade() +
                                ", Nome: " + especialidade.getNomeEspecialidade());
                    } else {
                        System.out.println("Especialidade não encontrado.");
                    }
                    break;
                case 3:
                    // Cadastrar novo produto
                    System.out.print("Digite o nome da especialidade: ");
                    scanner.nextLine(); // Limpar o buffer
                    String nome = scanner.nextLine();
                    controller.cadastrarEspecialidade(nome);
                    break;
                case 4:
                    // Deletar produto
                    System.out.print("Digite o código da especialidade a ser deletada: ");
                    codigo = scanner.nextInt();
                    controller.deletarEspecialidade(codigo);
                    break;
                case 5:
                    System.out.println("Digite o código da especialidade a ser atualizado:");
                    codigo = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    System.out.println("Digite a nova nome:");
                    String novoNome = scanner.nextLine();

                    controller.atualizarEspecialidade(codigo, novoNome);
                    break;

                case 6:
                    // Sair
                    running = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}
