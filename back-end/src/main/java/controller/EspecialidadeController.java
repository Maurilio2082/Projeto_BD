package controller;

import conexion.DatabaseConfig;
import model.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto especialidade.
 * ##########################################################################
 */

public class EspecialidadeController {

    public List<Especialidade> listarEspecialidades() {
        List<Especialidade> especialidades = new ArrayList<>();
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidades";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement especialidade = conexao.createStatement();
                ResultSet resultado = especialidade.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("id_especialidade");
                String nome = resultado.getString("nome_especialidade");
                especialidades.add(new Especialidade(codigo, nome));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public Especialidade buscarPorCodigoEspecialidade(int codigo) {
        Especialidade especialidades = null;
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidades WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement especialidade = conexao.prepareStatement(query)) {

            especialidade.setInt(1, codigo);
            ResultSet resultado = especialidade.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome_especialidade");
                especialidades = new Especialidade(codigo, nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public void cadastrarEspecialidade(String nome) {
        String query = "INSERT INTO especialidades (nome_especialidade) VALUES (?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement especialidade = conexao.prepareStatement(query)) {

            especialidade.setString(1, nome);
            especialidade.executeUpdate();

            System.out.println("Especialidade cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEspecialidade(int codigo) {
        String query = "DELETE FROM especialidades WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement especialidade = conexao.prepareStatement(query)) {

            especialidade.setInt(1, codigo);
            int registro = especialidade.executeUpdate();

            if (registro > 0) {
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

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement especialidade = conexao.prepareStatement(query)) {

            especialidade.setString(1, novoNome);
            especialidade.setInt(2, codigo);
            int registro = especialidade.executeUpdate();

            if (registro > 0) {
                System.out.println("Especialidade atualizada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
