package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Endereco;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto endereco.
 * ##########################################################################
 */

public class EnderecoController {

    public List<Endereco> listarEnderecos() {
        ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
        String query = "SELECT id_endereco, logradouro, numero, bairro, cidade, estado, cep FROM endereco";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idEndereco = resultado.getInt("id_endereco");
                String logradouro = resultado.getString("logradouro");
                String numero = resultado.getString("numero");

                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");

                Endereco endereco = new Endereco(idEndereco, logradouro, numero, bairro, cidade, estado,
                        cep);
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    public Endereco buscarEnderecoPorCodigo(int codigo) {
        Endereco endereco = null;
        String query = "SELECT id_endereco,logradouro, numero, bairro, cidade, estado, cep FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement enderecos = conexao.prepareStatement(query)) {

            enderecos.setInt(1, codigo);
            ResultSet resultado = enderecos.executeQuery();

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
        int idEndereco = -1; // Valor padrão caso a inserção falhe

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement endereco = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            endereco.setString(1, logradouro);
            endereco.setString(2, numero);
            endereco.setString(3, bairro);
            endereco.setString(4, cidade);
            endereco.setString(5, estado);
            endereco.setString(6, cep);
            endereco.executeUpdate();

            // Recuperar o id_endereco gerado
            ResultSet registro = endereco.getGeneratedKeys();
            if (registro.next()) {
                idEndereco = registro.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idEndereco;
    }

    public void atualizarEndereco(int codigo, String logradouro, String numero, String bairro,
            String cidade,
            String estado,
            String cep) {
        String query = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement endereco = conexao.prepareStatement(query)) {

            endereco.setInt(1, codigo);
            endereco.setString(2, logradouro);
            endereco.setString(3, numero);
            endereco.setString(4, bairro);
            endereco.setString(5, cidade);
            endereco.setString(6, estado);
            endereco.setString(7, cep);

            int registro = endereco.executeUpdate();

            if (registro > 0) {
                System.out.println("Endereco atualizada com sucesso!");
            } else {
                System.out.println("Endereco com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletarEndereco(int codigo) {
        String query = "DELETE FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement endereco = conexao.prepareStatement(query)) {

            endereco.setInt(1, codigo);
            int registro = endereco.executeUpdate();

            return registro > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
