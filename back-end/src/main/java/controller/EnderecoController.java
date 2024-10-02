package controller;

import java.sql.*;

import conexion.DatabaseConfig;
import model.Endereco;

public class EnderecoController {

    public void cadastrarEndereco(String longradouro, String numero, String bairro, String cidade, String estado,
            String cep) {
        String query = "INSERT INTO endereco(logradouro, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement Endereco = conexao.prepareStatement(query)) {

            Endereco.setString(1, longradouro);
            Endereco.setString(2, numero);
            Endereco.setString(3, bairro);
            Endereco.setString(4, cidade);
            Endereco.setString(5, estado);
            Endereco.setString(6, cep);

            Endereco.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEndereco(int codigo) {
        String query = "DELETE FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement Endereco = conexao.prepareStatement(query)) {

            Endereco.setInt(1, codigo);
            int registro = Endereco.executeUpdate();

            if (registro > 0) {
                System.out.println("Endereco deletado com sucesso!");
            } else {
                System.out.println("Endereco com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEndereco(int codigo, String longradouro, String numero, String bairro, String cidade,
            String estado,
            String cep) {
        String query = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement Endereco = conexao.prepareStatement(query)) {

            Endereco.setInt(1, codigo);
            Endereco.setString(2, longradouro);
            Endereco.setString(3, numero);
            Endereco.setString(4, bairro);
            Endereco.setString(5, cidade);
            Endereco.setString(6, estado);
            Endereco.setString(7, cep);

            int registro = Endereco.executeUpdate();

            if (registro > 0) {
                System.out.println("Especialidade atualizada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Endereco buscarEnderecoPorCodigo(int codigo) {
        Endereco endereco = null;
        String query = "SELECT id_endereco,logradouro, numero, bairro, cidade, estado, cep FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement Endereco = conexao.prepareStatement(query)) {

            Endereco.setInt(1, codigo);
            ResultSet resultado = Endereco.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id_endereco");
                String longradouro = resultado.getString("longradouro");
                String numero = resultado.getString("numero");
                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");
                endereco = new Endereco(id, longradouro, numero, bairro, cidade, estado, cep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return endereco;
    }

}
