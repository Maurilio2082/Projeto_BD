package controller;

import conexion.DatabaseConfig;
import model.Paciente;
import model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteController {

    public List<Paciente> listarPacientes() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
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

                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    public Paciente buscarPorCodigoPaciente(int codigo) {
        Paciente paciente = null;
        String query = "SELECT ID_PACIENTE, NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO FROM PACIENTE WHERE ID_PACIENTE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    public int cadastrarPaciente(String logradouro, String numero, String bairro, String cidade,
                                 String estado, String cep, String nomePaciente, String dataNascimento,
                                 String cpf, String email, String telefone) {

        EnderecoController enderecoController = new EnderecoController();

        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);
        int idPaciente = -1;

        if (idEndereco != -1) {
            String queryPaciente = "INSERT INTO PACIENTE (NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

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
                    idPaciente = registro.getInt(1);
                }

                System.out.println("Paciente cadastrado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Paciente não cadastrado.");
        }

        return idPaciente;
    }

    public void atualizarPaciente(int idPaciente, String logradouro, String numero, String bairro,
                                  String cidade, String estado, String cep, String nome, String dataNascimento,
                                  String cpf, String email, String telefone) {

        EnderecoController enderecoController = new EnderecoController();
        Paciente pacienteExistente = buscarPorCodigoPaciente(idPaciente);

        if (pacienteExistente != null && pacienteExistente.getIdEndereco() != null) {
            int idEndereco = pacienteExistente.getIdEndereco().getIdEndereco();
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, bairro, cidade, estado, cep);
        }

        String queryPaciente = "UPDATE PACIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, DATA_NASCIMENTO = ?, CPF = ? " +
                "WHERE ID_PACIENTE = ?";

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

    public void deletarHemocentro(int idPaciente) {
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
