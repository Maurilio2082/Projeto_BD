package controller;

import java.sql.*;
import java.util.Scanner;
import conexion.DatabaseConfig;
import model.Endereco;

public class EnderecoController {

    // Método para listar endereços com interação do usuário
    public void listarEnderecos() {
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

                System.out.println("ID: " + idEndereco + ", Logradouro: " + logradouro + ", Número: " + numero
                        + ", Bairro: " + bairro + ", Cidade: " + cidade + ", Estado: " + estado + ", CEP: " + cep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar endereço por código com interação do usuário
    public void buscarEnderecoPorCodigo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do endereço: ");
        int codigo = scanner.nextInt();

        String query = "SELECT id_endereco, logradouro, numero, bairro, cidade, estado, cep FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id_endereco");
                String logradouro = resultado.getString("logradouro");
                String numero = resultado.getString("numero");
                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");

                System.out.println("ID: " + id + ", Logradouro: " + logradouro + ", Número: " + numero
                        + ", Bairro: " + bairro + ", Cidade: " + cidade + ", Estado: " + estado + ", CEP: " + cep);
            } else {
                System.out.println("Endereço com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar endereço com interação do usuário
    public void cadastrarEndereco() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        String query = "INSERT INTO endereco(logradouro, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, logradouro);
            stmt.setString(2, numero);
            stmt.setString(3, bairro);
            stmt.setString(4, cidade);
            stmt.setString(5, estado);
            stmt.setString(6, cep);
            stmt.executeUpdate();

            ResultSet registro = stmt.getGeneratedKeys();
            if (registro.next()) {
                int idEndereco = registro.getInt(1);
                System.out.println("Endereço cadastrado com sucesso! ID: " + idEndereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar endereço com interação do usuário
    public void atualizarEndereco() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o código do endereço a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        String query = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, logradouro);
            stmt.setString(2, numero);
            stmt.setString(3, bairro);
            stmt.setString(4, cidade);
            stmt.setString(5, estado);
            stmt.setString(6, cep);
            stmt.setInt(7, codigo);

            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Endereço atualizado com sucesso!");
            } else {
                System.out.println("Endereço com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar endereço com interação do usuário
    public void deletarEndereco() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do endereço a ser deletado: ");
        int codigo = scanner.nextInt();

        String query = "DELETE FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            int registro = stmt.executeUpdate();

            if (registro > 0) {
                System.out.println("Endereço deletado com sucesso!");
            } else {
                System.out.println("Endereço com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
