package controller;

import model.Endereco;
import model.Paciente;
import Repository.PacienteRepository;

import java.util.List;
import java.util.Scanner;

public class PacienteController {

    private final PacienteRepository repository;
    private final EnderecoController enderecoController;
    private final Scanner scanner;

    public PacienteController() {
        this.repository = new PacienteRepository();
        this.enderecoController = new EnderecoController();
        this.scanner = new Scanner(System.in);
    }

    public void listarPacientes() {
        List<Paciente> pacientes = repository.buscarTodosPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    public void buscarPacientePorCpf() {
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();
        Paciente paciente = repository.buscarPorCpf(cpf);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
        } else {
            System.out.println(paciente);
        }
    }

    public void cadastrarPaciente() {
        System.out.println("Digite os dados do paciente:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Data de Nascimento: ");
        String dataNascimento = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
    
        // Cadastra o endereço
        System.out.println("Digite os dados do endereço do paciente:");
        Endereco endereco = enderecoController.cadastrarEndereco();
    
        // Cria o objeto paciente com o endereço associado
        Paciente paciente = new Paciente(null, nome, email, telefone, dataNascimento, cpf, endereco);
        repository.inserirPaciente(paciente);
    }
    

    public void atualizarPaciente() {
        System.out.print("Digite o CPF do paciente que deseja atualizar: ");
        String id = scanner.nextLine();
        Paciente pacienteAtual = repository.buscarPorCpf(id);

        if (pacienteAtual == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("Atualize os dados do paciente (ou deixe em branco para manter o atual):");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Data de Nascimento: ");
        String dataNascimento = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Endereco enderecoAtualizado = enderecoController.atualizarEndereco(pacienteAtual.getEndereco());

        Paciente pacienteAtualizado = new Paciente(
                id,
                nome.isEmpty() ? pacienteAtual.getNome() : nome,
                email.isEmpty() ? pacienteAtual.getEmail() : email,
                telefone.isEmpty() ? pacienteAtual.getTelefone() : telefone,
                dataNascimento.isEmpty() ? pacienteAtual.getDataNascimento() : dataNascimento,
                cpf.isEmpty() ? pacienteAtual.getCpf() : cpf,
                enderecoAtualizado);

        repository.atualizarPaciente(id, pacienteAtualizado);
    }

    public void deletarPaciente() {
        System.out.print("Digite o CPF do paciente que deseja excluir: ");
        String pacienteId = scanner.nextLine();

        Paciente paciente = repository.buscarPorCpf(pacienteId);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("Excluindo dependências relacionadas ao paciente...");
        enderecoController.excluirEndereco();

        repository.excluirPaciente(pacienteId);
        System.out.println("Paciente excluído com sucesso.");
    }
}
