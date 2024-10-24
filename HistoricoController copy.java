package controller;

import conexion.DatabaseConfig;
import model.Endereco;
import model.Historico;
import model.Hospital;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HistoricoController {
    
    // Método para listar o histórico com interação do usuário
    public void listarHistorico() {
        ArrayList<Historico> historicos = new ArrayList<>();
        PacienteController pacienteController = new PacienteController();
        HospitalController hospitalController = new HospitalController();

        String query = "SELECT ID_HISTORICO, DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL FROM HISTORICO";

        try (Connection conexao = DatabaseConfig.getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idHistorico = resultado.getInt("ID_HISTORICO");
                String dataConsulta = resultado.getString("DATA_CONSULTA");
                String observacao = resultado.getString("OBSERVACAO");
                int idPaciente = resultado.getInt("ID_PACIENTE");
                int idHospital = resultado.getInt("ID_HOSPITAL");

                Paciente paciente = pacienteController.buscarPorCodigoPaciente(idPaciente);
                Hospital hospital = hospitalController.buscarPorCodigoHospital(idHospital);

                Historico historico = new Historico(idHistorico, dataConsulta, observacao, paciente, hospital);
                historicos.add(historico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.printf("%-8s %-20s %-15s %-28s %-15s %-18s %-8s %n",
                "ID", "DataConsulta", "Observacao", "Razao Social", "CNPJ", "Paciente", "CPF");

        for (Historico lista : historicos) {
            System.out.println(lista);
        }
    }

    // Método para buscar um histórico por código com interação do usuário
    public void buscarPorCodigoHistorico() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do histórico: ");
        int codigo = scanner.nextInt();

        String query = "SELECT ID_HISTORICO, DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL FROM HISTORICO WHERE ID_HISTORICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idHistorico = resultado.getInt("ID_HISTORICO");
                String dataConsulta = resultado.getString("DATA_CONSULTA");
                String observacao = resultado.getString("OBSERVACAO");
                int idPaciente = resultado.getInt("ID_PACIENTE");
                int idHospital = resultado.getInt("ID_HOSPITAL");

                HospitalController hospitalController = new HospitalController();
                Hospital hospital = hospitalController.buscarPorCodigoHospital(idHospital);

                PacienteController pacienteController = new PacienteController();
                Paciente paciente = pacienteController.buscarPorCodigoPaciente(idPaciente);

                Historico historico = new Historico(idHistorico, dataConsulta, observacao, paciente, hospital);
                System.out.println(historico);
            } else {
                System.out.println("Histórico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar histórico com interação do usuário
    public void cadastrarHistorico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a data da consulta: ");
        String dataConsulta = scanner.nextLine();

        System.out.print("Digite a observação: ");
        String observacao = scanner.nextLine();

        System.out.print("Digite o CPF do paciente: ");
        String cpfPaciente = scanner.nextLine();

        System.out.print("Digite o CNPJ do hospital: ");
        String cnpjHospital = scanner.nextLine();

        Paciente paciente = buscarPacientePorCpf(cpfPaciente);
        Hospital hospital = buscarHospitalPorCnpj(cnpjHospital);

        if (paciente == null || hospital == null) {
            System.out.println("Paciente ou Hospital não encontrados.");
            return;
        }

        String query = "INSERT INTO HISTORICO (DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL) VALUES (?, ?, ?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, dataConsulta);
            stmt.setString(2, observacao);
            stmt.setInt(3, paciente.getIdPaciente());
            stmt.setInt(4, hospital.getIdHospital());

            stmt.executeUpdate();
            System.out.println("Histórico cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para alterar um histórico com interação do usuário
    public void alterarHistorico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do histórico a ser alterado: ");
        int idHistorico = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Digite a nova data da consulta: ");
        String dataConsulta = scanner.nextLine();

        System.out.print("Digite a nova observação: ");
        String observacao = scanner.nextLine();

        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();

        System.out.print("Digite o ID do hospital: ");
        int idHospital = scanner.nextInt();

        String query = "UPDATE HISTORICO SET DATA_CONSULTA = ?, OBSERVACAO = ?, ID_PACIENTE = ?, ID_HOSPITAL = ? WHERE ID_HISTORICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, dataConsulta);
            stmt.setString(2, observacao);
            stmt.setInt(3, idPaciente);
            stmt.setInt(4, idHospital);
            stmt.setInt(5, idHistorico);

            stmt.executeUpdate();
            System.out.println("Histórico atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar um histórico com interação do usuário
    public void deletarHistorico() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do histórico a ser deletado: ");
        int idHistorico = scanner.nextInt();

        String deleteHistorico = "DELETE FROM HISTORICO WHERE ID_HISTORICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(deleteHistorico)) {

            stmt.setInt(1, idHistorico);
            int registrosAfetados = stmt.executeUpdate();

            if (registrosAfetados > 0) {
                System.out.println("Histórico deletado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para buscar hospital por CNPJ
    public Hospital buscarHospitalPorCnpj(String cnpj) {
        String query = "SELECT * FROM HOSPITAL WHERE CNPJ = ?";
        EnderecoController enderecoController = new EnderecoController();
        Hospital hospital = null;

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, cnpj);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idHospital = resultado.getInt("ID_HOSPITAL");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }

    // Método auxiliar para buscar paciente por CPF
    public Paciente buscarPacientePorCpf(String cpf) {
        Paciente paciente = null;
        EnderecoController enderecoController = new EnderecoController();
        String query = "SELECT * FROM PACIENTE WHERE CPF = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, cpf);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }
}
