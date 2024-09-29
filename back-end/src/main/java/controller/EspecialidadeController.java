package controller;

import conexion.DatabaseConfig;
import model.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeController {

    public List<Especialidade> listarProdutos() {
        List<Especialidade> especialidades = new ArrayList<>();
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidades";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int codigo = rs.getInt("id_especialidade");
                String nome = rs.getString("nome_especialidade");
                especialidades.add(new Especialidade(codigo, nome));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    // controller/ProdutoController.java (adicionar método buscarPorCodigo)
    public Especialidade buscarPorCodigo(int codigo) {
        Especialidade especialidade = null;
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidades WHERE id_especialidade = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome_especialidade");
                especialidade = new Especialidade(codigo, nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidade;
    }

    public void cadastrarEspecialidade(String nome) {
        String query = "INSERT INTO especialidades (nome_especialidade) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();

            System.out.println("Especialidade cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEspecialidade(int codigo) {
        String query = "DELETE FROM especialidades WHERE id_especialidade = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Especialidade deletada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEspecialidade(int codigo, String novoNome) {
        String query = "UPDATE especialidades SET nome_especialidade = ? WHERE id_especialidade = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, novoNome);
            stmt.setInt(2, codigo);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Especialidade atualizada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
