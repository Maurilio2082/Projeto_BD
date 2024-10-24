package controller;

import conexion.DatabaseConfig;
import model.Medico;
import model.Especialidade;

import java.sql.*;
import java.util.Scanner;

public class EspecialidadeMedicoController {

    public void associarEspecialidadeAMedico() {
        Scanner scanner = new Scanner(System.in);

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

        String query = "INSERT INTO MEDICO_ESPECIALIDADE (ID_MEDICO, ID_ESPECIALIDADE) VALUES (?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idEspecialidade);
            stmt.executeUpdate();

            System.out.println("Especialidade associada ao médico com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerEspecialidadeDeMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o código do médico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o código da especialidade a ser removida: ");
        int idEspecialidade = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM MEDICO_ESPECIALIDADE WHERE ID_MEDICO = ? AND ID_ESPECIALIDADE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idEspecialidade);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Especialidade removida com sucesso!");
            } else {
                System.out.println("Especialidade ou médico não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Especialidade buscarPorCodigoEspecialidade(int codigo) {
        Especialidade especialidade = null;
        String query = "SELECT ID_ESPECIALIDADE, NOME FROM ESPECIALIDADE WHERE ID_ESPECIALIDADE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("NOME");
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
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

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
