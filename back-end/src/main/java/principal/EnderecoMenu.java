package principal;

import java.util.*;
import controller.EnderecoController;
import model.Endereco;

public class EnderecoMenu {

    public static void main(String[] args) {
        EnderecoController enderecoController = new EnderecoController();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("==== Menu de Endereços ====");
            System.out.println("1. Listar Endereços");
            System.out.println("2. Buscar Endereço por Código");
            System.out.println("3. Cadastrar Endereço");
            System.out.println("4. Atualizar Endereço");
            System.out.println("5. Deletar Endereço");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    List<Endereco> enderecos = enderecoController.listarEnderecos();
                    if (enderecos.isEmpty()) {
                        System.out.println("Nao ha enderecos cadastrados.");
                    } else {
                        System.out.printf("%-10s %-40s %-10s %-20s %-20s %-15s %-10s %-10s%n",
                                "ID", "Logradouro", "Número", "Complemento", "Bairro", "Cidade", "Estado", "CEP");
                        for (Endereco endereco : enderecos) {
                            System.out.println(endereco);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Digite o código do endereço: ");
                    int codigoBusca = scanner.nextInt();
                    Endereco enderecoBuscado = enderecoController.buscarEnderecoPorCodigo(codigoBusca);
                    if (enderecoBuscado != null) {
                        System.out.println(enderecoBuscado);
                    } else {
                        System.out.println("Endereço não encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Digite o logradouro: ");
                    String logradouro = scanner.nextLine();
                    System.out.print("Digite o número: ");
                    String numero = scanner.nextLine();
                    System.out.print("Digite o complemento: ");
                    String complemento = scanner.nextLine();
                    System.out.print("Digite o bairro: ");
                    String bairro = scanner.nextLine();
                    System.out.print("Digite a cidade: ");
                    String cidade = scanner.nextLine();
                    System.out.print("Digite o estado: ");
                    String estado = scanner.nextLine();
                    System.out.print("Digite o CEP: ");
                    String cep = scanner.nextLine();

                    int idNovoEndereco = enderecoController.cadastrarEndereco(logradouro, numero, complemento, bairro,
                            cidade, estado, cep);
                    if (idNovoEndereco != -1) {
                        System.out.println("Endereço cadastrado com sucesso! ID: " + idNovoEndereco);
                    } else {
                        System.out.println("Erro ao cadastrar o endereço.");
                    }
                    break;

                case 4:
                    System.out.print("Digite o código do endereço a ser atualizado: ");
                    int codigoAtualizar = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    System.out.print("Digite o logradouro: ");
                    logradouro = scanner.nextLine();
                    System.out.print("Digite o número: ");
                    numero = scanner.nextLine();
                    System.out.print("Digite o complemento: ");
                    complemento = scanner.nextLine();
                    System.out.print("Digite o bairro: ");
                    bairro = scanner.nextLine();
                    System.out.print("Digite a cidade: ");
                    cidade = scanner.nextLine();
                    System.out.print("Digite o estado: ");
                    estado = scanner.nextLine();
                    System.out.print("Digite o CEP: ");
                    cep = scanner.nextLine();

                    enderecoController.atualizarEndereco(codigoAtualizar, logradouro, numero, complemento, bairro,
                            cidade, estado, cep);
                    break;

                case 5:
                    System.out.print("Digite o código do endereço a ser deletado: ");
                    int codigoDeletar = scanner.nextInt();
                    boolean sucesso = enderecoController.deletarEndereco(codigoDeletar);
                    if (sucesso) {
                        System.out.println("Endereço deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o endereço.");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
