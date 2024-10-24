package controller;

import conexion.DatabaseConfig;
import model.Especialidade;

import java.sql.*;
import java.util.Scanner;

public class EspecialidadeController {

    // Método para listar especialidades com interação do usuário
    public void listarEspecialidades() {
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidade";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("id_especialidade");
                String nome = resultado.getString("nome_especialidade");
                System.out.println("ID: " + codigo + ", Nome: " + nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar especialidade por código com interação do usuário
    public void buscarPorCodigoEspecialidade() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código da especialidade: ");
        int codigo = scanner.nextInt();

        String query = "SELECT id_especialidade, nome_especialidade FROM especialidade WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome_especialidade");
                int idEspecialidade = resultado.getInt("id_especialidade");
                System.out.println("ID: " + idEspecialidade + ", Nome: " + nome);
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar especialidade com interação do usuário
    public void cadastrarEspecialidade() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da especialidade: ");
        String nome = scanner.nextLine();

        String query = "INSERT INTO especialidade (nome_especialidade) VALUES (?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();

            ResultSet registro = stmt.getGeneratedKeys();
            if (registro.next()) {
                int idEspecialidade = registro.getInt(1);
                System.out.println("Especialidade cadastrada com sucesso! ID: " + idEspecialidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar especialidade com interação do usuário
    public void deletarEspecialidade() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código da especialidade a ser deletada: ");
        int codigo = scanner.nextInt();

        String query = "DELETE FROM especialidade WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Especialidade deletada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar especialidade com interação do usuário
    public void atualizarEspecialidade() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o código da especialidade a ser atualizada: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Digite o novo nome da especialidade: ");
        String novoNome = scanner.nextLine();

        String query = "UPDATE especialidade SET nome_especialidade = ? WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, novoNome);
            stmt.setInt(2, codigo);
            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Especialidade atualizada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
