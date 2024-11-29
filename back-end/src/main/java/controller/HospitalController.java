package controller;

import model.Hospital;
import repository.HospitalRepository;
import utils.RemoverDependencia;
import model.Endereco;

import java.util.List;
import java.util.Scanner;

import org.bson.types.ObjectId;

public class HospitalController {

    private final HospitalRepository hospitalRepository;
    private final EnderecoController enderecoController;

    private final Scanner scanner;

    public HospitalController() {

        this.hospitalRepository = new HospitalRepository();
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
        hospitalRepository.inserirHospital(hospital);
    }

    public void atualizarHospital() {
        // Listar hospitais disponíveis para seleção
        List<Hospital> hospitais = hospitalRepository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado para atualização.");
            return;
        }

        System.out.println("Selecione o hospital que deseja atualizar:");
        for (int i = 0; i < hospitais.size(); i++) {
            Hospital hospital = hospitais.get(i);
            System.out.println((i + 1) + " - " + hospital.getRazaoSocial() + " (CNPJ: " + hospital.getCnpj() + ")");
        }

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

        Hospital hospitalAtual = hospitais.get(escolha - 1);

        // Exibir os dados atuais e permitir a edição
        System.out.println("Atualize os dados do hospital (ou deixe em branco para manter o atual):");

        System.out.print("Razão Social [" + hospitalAtual.getRazaoSocial() + "]: ");
        String razaoSocial = scanner.nextLine();

        System.out.print("CNPJ [" + hospitalAtual.getCnpj() + "]: ");
        String cnpj = scanner.nextLine();

        System.out.print("Email [" + hospitalAtual.getEmail() + "]: ");
        String email = scanner.nextLine();

        System.out.print("Telefone [" + hospitalAtual.getTelefone() + "]: ");
        String telefone = scanner.nextLine();

        System.out.print("Categoria [" + hospitalAtual.getCategoria() + "]: ");
        String categoria = scanner.nextLine();

        // Atualizar endereço associado
        Endereco enderecoAtualizado = enderecoController.atualizarEndereco(hospitalAtual.getEndereco());

        // Garantir que o ID do endereço esteja no formato correto (ObjectId)
        String enderecoId = enderecoAtualizado.getId();
        if (enderecoId != null && enderecoId.length() == 24) {
            enderecoAtualizado.setId(new ObjectId(enderecoId).toHexString());
        }

        // Criar objeto atualizado com os novos dados ou manter os existentes
        Hospital hospitalAtualizado = new Hospital(
                hospitalAtual.getId(),
                razaoSocial.isEmpty() ? hospitalAtual.getRazaoSocial() : razaoSocial,
                cnpj.isEmpty() ? hospitalAtual.getCnpj() : cnpj,
                email.isEmpty() ? hospitalAtual.getEmail() : email,
                telefone.isEmpty() ? hospitalAtual.getTelefone() : telefone,
                categoria.isEmpty() ? hospitalAtual.getCategoria() : categoria,
                enderecoAtualizado);

        // Atualizar o hospital no banco de dados
        hospitalRepository.atualizarHospital(hospitalAtual.getId(), hospitalAtualizado);
        System.out.println("Hospital atualizado com sucesso!");
    }

    public void deletarHospital() {
        // Listar todos os hospitais
        List<Hospital> hospitais = hospitalRepository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado para exclusão.");
            return;
        }

        // Exibir a lista de hospitais
        System.out.println("Selecione o hospital que deseja excluir:");
        for (int i = 0; i < hospitais.size(); i++) {
            Hospital hospital = hospitais.get(i);
            System.out.println((i + 1) + " - " + hospital.getRazaoSocial());
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

        // Remover dependências do hospital
        System.out.println("Removendo dependências relacionadas ao hospital...");
        RemoverDependencia.removerDependenciasHospital(hospitalSelecionado.getId());

        // Excluir o hospital
        hospitalRepository.excluirHospital(hospitalSelecionado.getId());
        System.out.println("Hospital excluído com sucesso!");
    }

}
