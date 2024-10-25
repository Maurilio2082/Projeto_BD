package controller;

import java.sql.*;

import conexion.DatabaseConfig;
import model.Endereco;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto endereco.
 * ##########################################################################
 */

public class EnderecoController {

    public Endereco buscarEnderecoPorCodigo(int codigo) {
        Endereco endereco = null;
        String query = "SELECT id_endereco,logradouro, numero, bairro, cidade, estado, cep FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id_endereco");
                String logradouro = resultado.getString("logradouro");
                String numero = resultado.getString("numero");
                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");
                endereco = new Endereco(id, logradouro, numero, bairro, cidade, estado, cep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return endereco;
    }

    public int cadastrarEndereco(String logradouro, String numero, String bairro, String cidade,
            String estado,
            String cep) {
        String query = "INSERT INTO endereco(logradouro, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";
        int idEndereco = -1;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, logradouro);
            statement.setString(2, numero);
            statement.setString(3, bairro);
            statement.setString(4, cidade);
            statement.setString(5, estado);
            statement.setString(6, cep);
            statement.executeUpdate();

            ResultSet registro = statement.getGeneratedKeys();
            if (registro.next()) {
                idEndereco = registro.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idEndereco;
    }

    public void atualizarEndereco(int codigo, String logradouro, String numero, String bairro,
            String cidade, String estado, String cep) {
        String query = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, logradouro);
            statement.setString(2, numero);
            statement.setString(3, bairro);
            statement.setString(4, cidade);
            statement.setString(5, estado);
            statement.setString(6, cep);
            statement.setInt(7, codigo);

            int registro = statement.executeUpdate();

            if (registro > 0) {
                System.out.println("Endereço atualizado com sucesso!");
            } else {
                System.out.println("Endereço com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletarEndereco(int codigo) {
        String query = "DELETE FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            int registro = statement.executeUpdate();

            return registro > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
