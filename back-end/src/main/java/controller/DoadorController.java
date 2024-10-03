package controller;

import conexion.DatabaseConfig;
import model.Doador;
import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto doador.
 * ##########################################################################
 */

public class DoadorController {

    public List<Doador> listarDoadores() {
        ArrayList<Doador> doadores = new ArrayList<Doador>();
        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.tipo_sanguineo, d.data_nascimento, "
                + "e.id_endereco, e.longradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep "
                + "FROM doador d "
                + "JOIN endereco e ON d.id_endereco = e.id_endereco";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement doador = conexao.createStatement();
                ResultSet resultado = doador.executeQuery(query)) {

            while (resultado.next()) {
                int idDoador = resultado.getInt("id_doador");
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                String telefone = resultado.getString("telefone");
                String tipoSanguineo = resultado.getString("tipo_sanguineo");
                String dataNascimento = resultado.getString("data_nascimento");

                int idEndereco = resultado.getInt("id_endereco");
                String logradouro = resultado.getString("longradouro");
                String numero = resultado.getString("numero");
                String complemento = resultado.getString("complemento");
                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");

                Endereco endereco = new Endereco(idEndereco, logradouro, numero, complemento, bairro, cidade, estado,
                        cep);

                Doador doadorObj = new Doador(idDoador, nome, cpf, email, telefone, tipoSanguineo, dataNascimento);

                doadorObj.setId_endereco(endereco);

                doadores.add(doadorObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doadores;
    }

    public Doador buscarPorCodigoDoador(int codigo) {
        Doador doador = null;
        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.tipo_sanguineo, d.data_nascimento, " +
                "e.id_endereco, e.logradouro, e.numero, e.bairro, e.cidade, e.estado, e.cep " +
                "FROM doador d " +
                "JOIN endereco e ON d.id_endereco = e.id_endereco " +
                "WHERE d.id_doador = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idDoador = resultado.getInt("id_doador");
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                String telefone = resultado.getString("telefone");
                String tipoSanguineo = resultado.getString("tipo_sanguineo");
                String dataNascimento = resultado.getString("data_nascimento");

                int idEndereco = resultado.getInt("id_endereco");
                String logradouro = resultado.getString("logradouro");
                String numero = resultado.getString("numero");
                String bairro = resultado.getString("bairro");
                String cidade = resultado.getString("cidade");
                String estado = resultado.getString("estado");
                String cep = resultado.getString("cep");

                Endereco endereco = new Endereco(idEndereco, logradouro, numero, null, bairro, cidade, estado, cep);
                doador = new Doador(idDoador, nome, cpf, email, telefone, tipoSanguineo, dataNascimento);
                doador.setId_endereco(endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doador;
    }

    public void cadastrarDoador(String logradouro, String numero, String bairro, String cidade, String estado,
            String cep, String nome, String cpf, String email, String telefone, String tipoSanguineo,
            String dataNascimento) {

        EnderecoController enderecoController = new EnderecoController();

        // Cadastrar o endereço e obter o ID gerado
        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);

        if (idEndereco != -1) { // Verifica se o endereço foi cadastrado com sucesso
            String queryDoador = "INSERT INTO doador (nome, cpf, email, telefone, tipo_sanguineo, data_nascimento, id_endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement doador = conexao.prepareStatement(queryDoador)) {

                // Inserir o doador com o id_endereco recuperado
                doador.setString(1, nome);
                doador.setString(2, cpf);
                doador.setString(3, email);
                doador.setString(4, telefone);
                doador.setString(5, tipoSanguineo);
                doador.setString(6, dataNascimento);
                doador.setInt(7, idEndereco);

                doador.executeUpdate();
                System.out.println("Doador cadastrado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Doador não cadastrado.");
        }
    }

    public void atualizarDoador(int idDoador, String logradouro, String numero, String bairro, String cidade,
            String estado, String cep, String nome, String cpf, String email, String telefone,
            String tipoSanguineo, String dataNascimento) {

        EnderecoController enderecoController = new EnderecoController();

        // Atualizar o endereço
        Doador doadorExistente = buscarPorCodigoDoador(idDoador);
        if (doadorExistente != null && doadorExistente.getId_endereco() != null) {
            int idEndereco = doadorExistente.getId_endereco().getId_endereco();
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, bairro, cidade, estado, cep);
        }

        // Atualizar o doador
        String queryDoador = "UPDATE doador SET nome = ?, cpf = ?, email = ?, telefone = ?, tipo_sanguineo = ?, " +
                "data_nascimento = ? WHERE id_doador = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setString(5, tipoSanguineo);
            stmt.setString(6, dataNascimento);
            stmt.setInt(7, idDoador);

            stmt.executeUpdate();
            System.out.println("Doador atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarDoador(int idDoador) {
        Doador doador = buscarPorCodigoDoador(idDoador);
        if (doador != null) {

            int idEndereco = -1;
            if (doador.getId_endereco() != null) {
                idEndereco = doador.getId_endereco().getId_endereco();
            }

            String queryDoador = "DELETE FROM doador WHERE id_doador = ?";
            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

                stmt.setInt(1, idDoador);
                int registrosDeletados = stmt.executeUpdate();

                if (registrosDeletados > 0) {
                    System.out.println("Doador deletado com sucesso!");

                    if (idEndereco != -1) {
                        EnderecoController enderecoController = new EnderecoController();
                        enderecoController.deletarEndereco(idEndereco);
                    }
                } else {
                    System.out.println("Doador com ID " + idDoador + " não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Doador com ID " + idDoador + " não encontrado.");
        }
    }

}
