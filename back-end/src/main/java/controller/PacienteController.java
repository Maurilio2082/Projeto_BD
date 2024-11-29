package controller;

import model.Endereco;
import model.Paciente;
import repository.PacienteRepository;

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
        // Listar todos os pacientes
        List<Paciente> pacientes = repository.buscarTodosPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado para atualização.");
            return;
        }

        System.out.println("Selecione o paciente que deseja atualizar:");
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            System.out.println((i + 1) + " - " + paciente.getNome() + " (CPF: " + paciente.getCpf() + ")");
        }

        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + pacientes.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= pacientes.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        Paciente pacienteAtual = pacientes.get(escolha - 1);

        System.out.println("Atualize os dados do paciente (ou deixe em branco para manter o atual):");
        System.out.print("Nome [" + pacienteAtual.getNome() + "]: ");
        String nome = scanner.nextLine();

        System.out.print("Email [" + pacienteAtual.getEmail() + "]: ");
        String email = scanner.nextLine();

        System.out.print("Telefone [" + pacienteAtual.getTelefone() + "]: ");
        String telefone = scanner.nextLine();

        System.out.print("Data de Nascimento [" + pacienteAtual.getDataNascimento() + "]: ");
        String dataNascimento = scanner.nextLine();

        System.out.print("CPF [" + pacienteAtual.getCpf() + "]: ");
        String cpf = scanner.nextLine();

        // Atualizar o endereço associado
        System.out.println("Atualize os dados do endereço associado:");
        Endereco enderecoAtualizado = enderecoController.atualizarEndereco(pacienteAtual.getEndereco());

        // Criar o objeto atualizado
        Paciente pacienteAtualizado = new Paciente(
                pacienteAtual.getId(),
                nome.isEmpty() ? pacienteAtual.getNome() : nome,
                email.isEmpty() ? pacienteAtual.getEmail() : email,
                telefone.isEmpty() ? pacienteAtual.getTelefone() : telefone,
                dataNascimento.isEmpty() ? pacienteAtual.getDataNascimento() : dataNascimento,
                cpf.isEmpty() ? pacienteAtual.getCpf() : cpf,
                enderecoAtualizado);

        // Atualizar no repositório
        repository.atualizarPaciente(pacienteAtual.getId(), pacienteAtualizado);
        System.out.println("Paciente atualizado com sucesso!");
    }

    public void deletarPaciente() {
        // Listar todos os pacientes
        List<Paciente> pacientes = repository.buscarTodosPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado para exclusão.");
            return;
        }

        // Exibir a lista de pacientes
        System.out.println("Selecione o paciente que deseja excluir:");
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            System.out.println((i + 1) + " - " + paciente.getNome() + " (CPF: " + paciente.getCpf() + ")");
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + pacientes.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= pacientes.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar o paciente
        Paciente pacienteSelecionado = pacientes.get(escolha - 1);

        // Verificar e excluir o endereço associado
        Endereco enderecoAssociado = pacienteSelecionado.getEndereco();
        if (enderecoAssociado != null && enderecoAssociado.getId() != null) {
            try {
                enderecoController.excluirEndereco(enderecoAssociado.getId());
                System.out.println("Endereço associado excluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao excluir endereço associado: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum endereço válido associado encontrado para exclusão.");
        }

        // Excluir o paciente
        try {
            repository.excluirPaciente(pacienteSelecionado.getId());
            System.out.println("Paciente excluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir paciente: " + e.getMessage());
        }
    }

}
