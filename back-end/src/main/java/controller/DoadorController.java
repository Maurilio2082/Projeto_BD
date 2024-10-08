package controller;

import conexion.DatabaseConfig;
import model.Doador;
import model.Endereco;
import model.TipoSanguineo;
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
        ArrayList<Doador> doadores = new ArrayList<>();
        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.tipo_sanguineo, d.data_nascimento, " +
                "d.id_endereco,d.id_tipo_sanguineo " +
                "FROM doador d";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idDoador = resultado.getInt("id_doador");
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                String telefone = resultado.getString("telefone");
                String tipoSangue = resultado.getString("tipo_sanguineo");
                String dataNascimento = resultado.getString("data_nascimento");
                int idEndereco = resultado.getInt("id_endereco");
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                TipoSanguineo tipoSanguineo = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);

                Doador doador = new Doador(idDoador, nome, cpf, email, telefone, tipoSangue, dataNascimento);

                doador.setIdEndereco(endereco);
                doador.setIdTipoSanguineo(tipoSanguineo);
                doadores.add(doador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doadores;
    }

    // Buscar doador por código, usando EnderecoController para manipular endereços
    public Doador buscarPorCodigoDoador(int codigo) {
        Doador doador = null;
        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.tipo_sanguineo, d.data_nascimento, " +
                "d.id_endereco " +
                "FROM doador d " +
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
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");

                // Usar EnderecoController para buscar o endereço
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                TipoSanguineo tipoSangui = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);
                doador = new Doador(idDoador, nome, cpf, email, telefone, tipoSanguineo, dataNascimento);
                doador.setIdEndereco(endereco);
                doador.setIdTipoSanguineo(tipoSangui);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doador;
    }

    public void cadastrarDoador(String logradouro, String numero, String complemento, String bairro, String cidade,
            String estado, String cep,
            String nome, String cpf, String email, String telefone,
            String tipoSanguineo, String dataNascimento, String tipoSangue, String fatorRh) {

        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        int idTipoSanguineo = tipoSanguineoController.cadastrarTipoSanguineo(tipoSangue, fatorRh);
        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, complemento, bairro, cidade, estado,
                cep);

        if (idEndereco != -1) {
            String queryDoador = "INSERT INTO doador (nome, cpf, email, telefone, tipo_sanguineo, data_nascimento, id_endereco,id_tipo_sanguineo) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

                stmt.setString(1, nome);
                stmt.setString(2, cpf);
                stmt.setString(3, email);
                stmt.setString(4, telefone);
                stmt.setString(5, tipoSanguineo);
                stmt.setString(6, dataNascimento);
                stmt.setInt(7, idEndereco);
                stmt.setInt(8, idTipoSanguineo);

                stmt.executeUpdate();
                System.out.println("Doador cadastrado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Doador não cadastrado.");
        }
    }

    // Atualizar doador e usar EnderecoController para manipular endereços
    public void atualizarDoador(int idDoador, String logradouro, String numero, String complemento, String bairro,
            String cidade,
            String estado, String cep, String nome, String cpf, String email,
            String telefone, String tipoSanguineo, String dataNascimento, String tipoSangue, String fatorRh) {

        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        // Atualizar o endereço
        Doador doadorExistente = buscarPorCodigoDoador(idDoador);
        if (doadorExistente != null && doadorExistente.getIdEndereco() != null
                && doadorExistente.getIdTipoSanguineo() != null) {
            int idEndereco = doadorExistente.getIdEndereco().getId_endereco();
            int idTipoSanguineo = doadorExistente.getIdTipoSanguineo().getIdTipoSanguineo();

            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, complemento, bairro, cidade, estado,
                    cep);

            tipoSanguineoController.atualizarTipoSanguineo(idTipoSanguineo, tipoSangue, fatorRh);

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

    // Deletar doador e endereço
    public void deletarDoador(int idDoador) {
        Doador doadorExistente = buscarPorCodigoDoador(idDoador);
        if (doadorExistente != null && doadorExistente.getIdEndereco() != null
                && doadorExistente.getIdTipoSanguineo() != null) {
            int idEndereco = doadorExistente.getIdEndereco().getId_endereco();
            int idTipoSanguineo = doadorExistente.getIdTipoSanguineo().getIdTipoSanguineo();

            EnderecoController enderecoController = new EnderecoController();
            TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();
            enderecoController.deletarEndereco(idEndereco);
            tipoSanguineoController.deletarTipoSanguineo(idTipoSanguineo);
        }

        String query = "DELETE FROM doador WHERE id_doador = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, idDoador);
            stmt.executeUpdate();
            System.out.println("Doador deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}