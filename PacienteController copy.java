package controller;

import conexion.DatabaseConfig;
import model.Paciente;
import model.Endereco;

import java.sql.*;
import java.util.Scanner;

public class PacienteController {

    // Método para listar pacientes com interação do usuário
    public void listarPacientes() {
        EnderecoController enderecoController = new EnderecoController();
        String query = "SELECT ID_PACIENTE, NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO FROM PACIENTE;";

        try (Connection conexao = DatabaseConfig.getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                Paciente paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf, endereco);

                System.out.println(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar paciente por código com interação do usuário
    public void buscarPorCodigoPaciente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do paciente: ");
        int codigo = scanner.nextInt();

        String query = "SELECT ID_PACIENTE, NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO FROM PACIENTE WHERE ID_PACIENTE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                Paciente paciente = new Paciente(codigo, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
                System.out.println(paciente);
            } else {
                System.out.println("Paciente com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar paciente com interação do usuário
    public void cadastrarPaciente() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Digite o nome do paciente: ");
        String nomePaciente = scanner.nextLine();

        System.out.print("Digite a data de nascimento do paciente (YYYY-MM-DD): ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o email do paciente: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone do paciente: ");
        String telefone = scanner.nextLine();

        EnderecoController enderecoController = new EnderecoController();
        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);

        if (idEndereco != -1) {
            String queryPaciente = "INSERT INTO PACIENTE (NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(queryPaciente, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, nomePaciente);
                stmt.setString(2, email);
                stmt.setString(3, telefone);
                stmt.setString(4, dataNascimento);
                stmt.setString(5, cpf);
                stmt.setInt(6, idEndereco);
                stmt.executeUpdate();

                ResultSet registro = stmt.getGeneratedKeys();
                if (registro.next()) {
                    int idPaciente = registro.getInt(1);
                    System.out.println("Paciente cadastrado com sucesso! ID: " + idPaciente);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Paciente não cadastrado.");
        }
    }

    // Método para atualizar paciente com interação do usuário
    public void atualizarPaciente() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do paciente a ser atualizado: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Digite o nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a data de nascimento do paciente (YYYY-MM-DD): ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o email do paciente: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone do paciente: ");
        String telefone = scanner.nextLine();

        EnderecoController enderecoController = new EnderecoController();
        Paciente pacienteExistente = buscarPorCodigoPaciente(idPaciente);

        if (pacienteExistente != null && pacienteExistente.getIdEndereco() != null) {
            int idEndereco = pacienteExistente.getIdEndereco().getIdEndereco();
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, bairro, cidade, estado, cep);
        }

        String queryPaciente = "UPDATE PACIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, DATA_NASCIMENTO = ?, CPF = ? WHERE ID_PACIENTE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(queryPaciente)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            stmt.setString(4, dataNascimento);
            stmt.setString(5, cpf);
            stmt.setInt(6, idPaciente);
            stmt.executeUpdate();

            System.out.println("Paciente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar paciente com interação do usuário
    public void deletarPaciente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do paciente a ser deletado: ");
        int idPaciente = scanner.nextInt();

        Paciente pacienteExistente = buscarPorCodigoPaciente(idPaciente);
        if (pacienteExistente != null && pacienteExistente.getIdEndereco() != null) {
            int idEndereco = pacienteExistente.getIdEndereco().getIdEndereco();
            EnderecoController enderecoController = new EnderecoController();

            String queryPaciente = "DELETE FROM PACIENTE WHERE ID_PACIENTE = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                 PreparedStatement stmtPaciente = conexao.prepareStatement(queryPaciente)) {

                stmtPaciente.setInt(1, idPaciente);
                int registrosAfetados = stmtPaciente.executeUpdate();

                if (registrosAfetados > 0) {
                    System.out.println("Paciente deletado com sucesso!");

                    if (enderecoController.deletarEndereco(idEndereco)) {
                        System.out.println("Endereço deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o endereço.");
                    }
                } else {
                    System.out.println("Erro ao deletar o paciente. Paciente não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Paciente não encontrado ou não possui endereço associado.");
        }
    }
}
