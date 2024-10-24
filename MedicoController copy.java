package controller;

import conexion.DatabaseConfig;
import model.Medico;

import java.sql.*;
import java.util.Scanner;

public class MedicoController {

    // Método para listar médicos com interação do usuário
    public void listarMedicos() {
        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO";

        try (Connection conexao = DatabaseConfig.getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("ID_MEDICO");
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                System.out.printf("ID: %d, Nome: %s, Conselho: %s%n", codigo, nome, conselho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar médico por código com interação do usuário
    public void buscarPorCodigoMedico() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do médico: ");
        int codigo = scanner.nextInt();

        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                System.out.printf("ID: %d, Nome: %s, Conselho: %s%n", codigo, nome, conselho);
            } else {
                System.out.println("Médico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar médico com interação do usuário
    public void cadastrarMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do médico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o conselho do médico: ");
        String conselho = scanner.nextLine();

        String query = "INSERT INTO MEDICO (NOME, CONSELHO) VALUES (?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, conselho);
            stmt.executeUpdate();

            ResultSet registro = stmt.getGeneratedKeys();
            if (registro.next()) {
                int idMedico = registro.getInt(1);
                System.out.println("Médico cadastrado com sucesso! ID: " + idMedico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar médico com interação do usuário
    public void deletarMedico() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do médico a ser deletado: ");
        int codigo = scanner.nextInt();

        String query = "DELETE FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Médico deletado com sucesso!");
            } else {
                System.out.println("Médico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar médico com interação do usuário
    public void atualizarMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o código do médico a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        System.out.print("Digite o novo nome do médico: ");
        String novoNome = scanner.nextLine();

        System.out.print("Digite o novo conselho do médico: ");
        String conselho = scanner.nextLine();

        String query = "UPDATE MEDICO SET NOME = ?, CONSELHO = ? WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, novoNome);
            stmt.setString(2, conselho);
            stmt.setInt(3, codigo);
            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Médico atualizado com sucesso!");
            } else {
                System.out.println("Médico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
