package controller;

import conexion.DatabaseConfig;
import model.Medico;
import model.Especialidade;

import java.sql.*;
import java.util.Scanner;

public class EspecialidadeMedicoController {

    private final Scanner scanner = new Scanner(System.in);

    public void cadastrarEspecialidadeXMedico() {
        System.out.println("Cadastro de Especialidade X Médico:");

        System.out.print("Digite o código do médico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarPorCodigoMedico(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Digite o código da especialidade: ");
        int idEspecialidade = scanner.nextInt();
        scanner.nextLine();

        Especialidade especialidade = buscarPorCodigoEspecialidade(idEspecialidade);
        if (especialidade == null) {
            System.out.println("Especialidade não encontrada.");
            return;
        }

        if (verificarRelacionamentoExistente(idMedico, idEspecialidade)) {
            System.out.println("Esse relacionamento já existe entre o médico e a especialidade.");
            return;
        }

        String query = "INSERT INTO ESPECIALIDADE_MEDICO (ID_MEDICO, ID_ESPECIALIDADE) VALUES (?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idMedico);
            statement.setInt(2, idEspecialidade);
            statement.executeUpdate();

            System.out.println("Especialidade associada ao médico com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarRelacionamentoExistente(int idMedico, int idEspecialidade) {
        String query = "SELECT COUNT(*) FROM ESPECIALIDADE_MEDICO WHERE ID_MEDICO = ? AND ID_ESPECIALIDADE = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idMedico);
            statement.setInt(2, idEspecialidade);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                return resultado.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void atualizarEspecialidadeXMedico() {
        System.out.println("Alterar Especilidade X Medico:");

        System.out.print("Digite o codigo do medico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarPorCodigoMedico(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Digite o codigo da especialidade atual: ");
        int idEspecialidadeAtual = scanner.nextInt();
        scanner.nextLine();

        Especialidade especialidadeAtual = buscarPorCodigoEspecialidade(idEspecialidadeAtual);
        if (especialidadeAtual == null) {
            System.out.println("Especialidade atual nao encontrada.");
            return;
        }

        System.out.print("Digite o codigo da nova especialidade: ");
        int idNovaEspecialidade = scanner.nextInt();
        scanner.nextLine();

        Especialidade novaEspecialidade = buscarPorCodigoEspecialidade(idNovaEspecialidade);
        if (novaEspecialidade == null) {
            System.out.println("Nova especialidade nao encontrada.");
            return;
        }

        String query = "UPDATE ESPECIALIDADE_MEDICO SET ID_ESPECIALIDADE = ? WHERE ID_MEDICO = ? AND ID_ESPECIALIDADE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idNovaEspecialidade);
            statement.setInt(2, idMedico);
            statement.setInt(3, idEspecialidadeAtual);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Especialidade alterada com sucesso");
            } else {
                System.out.println("Especialidade ou médico não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEspecialidadeXMedico() {
        System.out.println("Deletar Especilidade X Medico:");

        System.out.print("Digite o codigo do medico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o codigo da especialidade a ser removida: ");
        int idEspecialidade = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM ESPECIALIDADE_MEDICO WHERE ID_MEDICO = ? AND ID_ESPECIALIDADE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idMedico);
            statement.setInt(2, idEspecialidade);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Especialidade removida com sucesso");
            } else {
                System.out.println("Especialidade ou medico nao encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Especialidade buscarPorCodigoEspecialidade(int codigo) {
        Especialidade especialidade = null;
        String query = "SELECT ID_ESPECIALIDADE, NOME_ESPECIALIDADE FROM ESPECIALIDADE WHERE ID_ESPECIALIDADE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("NOME_ESPECIALIDADE");
                especialidade = new Especialidade(codigo, nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidade;
    }

    public Medico buscarPorCodigoMedico(int codigo) {
        Medico medico = null;
        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                medico = new Medico(codigo, nome, conselho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }
}
