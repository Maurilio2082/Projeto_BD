package controller;

import model.Hospital;
import model.Endereco;

import java.util.List;
import java.util.Scanner;

import Repository.HospitalRepository;

public class HospitalController {

    private final HospitalRepository repository;
    private final EnderecoController enderecoController;
    private final Scanner scanner;

    public HospitalController() {
        this.repository = new HospitalRepository();
        this.enderecoController = new EnderecoController();
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarHospital() {
        System.out.println("Digite os dados do hospital:");
        System.out.print("Razão Social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.println("Digite os dados do endereço do hospital:");
        Endereco endereco = enderecoController.cadastrarEndereco();

        Hospital hospital = new Hospital(null, razaoSocial, cnpj, email, telefone, categoria, endereco);
        repository.inserirHospital(hospital);
    }

    public void atualizarHospital() {
        System.out.print("Digite o CNPJ do hospital que deseja atualizar: ");
        String id = scanner.nextLine();
        Hospital hospitalAtual = repository.buscarPorCnpj(id);

        if (hospitalAtual == null) {
            System.out.println("Hospital não encontrado.");
            return;
        }

        System.out.println("Atualize os dados (ou deixe em branco para manter o atual):");

        System.out.print("Razão Social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        Endereco enderecoAtualizado = enderecoController.atualizarEndereco(hospitalAtual.getEndereco());

        Hospital hospitalAtualizado = new Hospital(
                id,
                razaoSocial.isEmpty() ? hospitalAtual.getRazaoSocial() : razaoSocial,
                cnpj.isEmpty() ? hospitalAtual.getCnpj() : cnpj,
                email.isEmpty() ? hospitalAtual.getEmail() : email,
                telefone.isEmpty() ? hospitalAtual.getTelefone() : telefone,
                categoria.isEmpty() ? hospitalAtual.getCategoria() : categoria,
                enderecoAtualizado);

        repository.atualizarHospital(id, hospitalAtualizado);
    }

    public void deletarHospital() {
        // Listar todos os hospitais
        List<Hospital> hospitais = repository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado para exclusão.");
            return;
        }
    
        // Exibir a lista de hospitais
        System.out.println("Selecione o hospital que deseja excluir:");
        for (int i = 0; i < hospitais.size(); i++) {
            Hospital hospital = hospitais.get(i);
            System.out.println((i + 1) + " - " + hospital.getRazaoSocial() + " (CNPJ: " + hospital.getCnpj() + ")");
        }
    
        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + hospitais.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= hospitais.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    
        // Selecionar o hospital
        Hospital hospitalSelecionado = hospitais.get(escolha - 1);
    
        // Verificar e excluir o endereço associado
        Endereco enderecoAssociado = hospitalSelecionado.getEndereco();
        if (enderecoAssociado != null && enderecoAssociado.getId() != null
                && enderecoAssociado.getId().length() == 24) {
            try {
                enderecoController.excluirEndereco(enderecoAssociado.getId());
                System.out.println("Endereço associado excluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao excluir o endereço associado: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum endereço válido associado encontrado para exclusão.");
        }
    
        // Excluir o hospital
        repository.excluirHospital(hospitalSelecionado.getId());
        System.out.println("Hospital excluído com sucesso!");
    }
    

}
