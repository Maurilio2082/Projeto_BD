package controller;

import model.Hospital;
import model.Endereco;

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
        System.out.print("Digite o ID do hospital que deseja excluir: ");
        String id = scanner.nextLine();
        repository.excluirHospital(id);
    }
}
