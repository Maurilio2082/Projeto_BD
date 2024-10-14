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
 * # Classe usada para criar os métodos que gerenciam o objeto doador.
 * ##########################################################################
 */

public class DoadorController {

    public List<Doador> listarDoadores() {
        ArrayList<Doador> doadores = new ArrayList<>();
        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.data_nascimento, d.peso, d.quantidade_doacoes, d.id_endereco, d.id_tipo_sanguineo "
                +
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
                String dataNascimento = resultado.getString("data_nascimento");
                String peso = resultado.getString("peso");
                String quantidadeDoacoes = resultado.getString("quantidade_doacoes");
                int idEndereco = resultado.getInt("id_endereco");
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                TipoSanguineo tipoSanguineo = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);

                Doador doador = new Doador(idDoador, nome, cpf, email, telefone, dataNascimento, peso,
                        quantidadeDoacoes, endereco, tipoSanguineo);

                doadores.add(doador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Cabeçalho da tabela
        System.out.printf("%-8s %-20s %-15s %-28s %-15s %-18s %-8s %-15s %-28s %-15s %-15s %-15s %n",
                "ID", "Nome", "CPF", "Email", "Telefone", "Data Nascimento", "Peso", "Qts Doacoes",
                "Logradouro", "Bairro", "Cidade", "Estado");

        for (Doador doador : doadores) {
            System.out.println(doador);
        }

        return doadores;
    }

    // Buscar doador por código, usando EnderecoController para manipular endereços
    public Doador buscarPorCodigoDoador(int codigo) {
        Doador doador = null;

        String query = "SELECT d.id_doador, d.nome, d.cpf, d.email, d.telefone, d.data_nascimento, d.peso, d.quantidade_doacoes, "
                +
                "d.id_endereco, d.id_tipo_sanguineo " +
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
                String dataNascimento = resultado.getString("data_nascimento");
                int idEndereco = resultado.getInt("id_endereco");
                String peso = resultado.getString("peso");
                String quantidadeDoacoes = resultado.getString("quantidade_doacoes");
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();
                TipoSanguineo tipoSangui = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);

                doador = new Doador(idDoador, nome, cpf, email, telefone, dataNascimento, peso,
                        quantidadeDoacoes, endereco, tipoSangui);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doador;
    }

    public int cadastrarDoador(String logradouro, String numero, String complemento, String bairro, String cidade,
            String estado, String cep, String nome, String cpf, String email, String telefone,
            String peso, String quantidadeDoacoes, String dataNascimento, String tipoSangue, String fatorRh) {

        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        // Cadastrar endereço
        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, complemento, bairro, cidade, estado,
                cep);

        if (idEndereco == -1) {
            System.out.println("Erro ao cadastrar o endereço. Doador não cadastrado.");
            return -1; // Retorne -1 se o endereço não puder ser cadastrado
        }

        // Cadastrar tipo sanguíneo
        int idTipoSanguineo = tipoSanguineoController.cadastrarTipoSanguineo(tipoSangue, fatorRh);
        int idDoador = -1;

        // Cadastrar doador
        String queryDoador = "INSERT INTO doador (nome, cpf, email, telefone, data_nascimento, id_endereco, peso, quantidade_doacoes, id_tipo_sanguineo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryDoador,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setString(5, dataNascimento);
            stmt.setInt(6, idEndereco);
            stmt.setString(7, peso);
            stmt.setString(8, quantidadeDoacoes);
            stmt.setInt(9, idTipoSanguineo);
            stmt.executeUpdate();

            ResultSet registro = stmt.getGeneratedKeys();
            if (registro.next()) {
                idDoador = registro.getInt(1);
            }

            System.out.println("Doador cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idDoador;
    }

    // Atualizar doador e usar EnderecoController para manipular endereços
    public void atualizarDoador(int idDoador, String logradouro, String numero, String complemento, String bairro,
            String cidade,
            String estado, String cep, String nome, String cpf, String email, String telefone,
            String peso, String quantidadeDoacoes, String dataNascimento, String tipoSangue, String fatorRh) {

        EnderecoController enderecoController = new EnderecoController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        // Atualizar o endereço
        Doador doadorExistente = buscarPorCodigoDoador(idDoador);
        if (doadorExistente != null && doadorExistente.getIdEndereco() != null
                && doadorExistente.getIdTipoSanguineo() != null) {
            int idEndereco = doadorExistente.getIdEndereco().getIdEndereco();
            int idTipoSanguineo = doadorExistente.getIdTipoSanguineo().getIdTipoSanguineo();

            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, complemento, bairro, cidade, estado,
                    cep);
            tipoSanguineoController.atualizarTipoSanguineo(idTipoSanguineo, tipoSangue, fatorRh);
        }

        // Atualizar o doador
        String queryDoador = "UPDATE doador SET nome = ?, cpf = ?, email = ?, telefone = ?, peso = ?, quantidade_doacoes = ?, id_tipo_sanguineo = ?, data_nascimento = ? WHERE id_doador = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setString(5, peso);
            stmt.setString(6, quantidadeDoacoes);
            stmt.setInt(7, doadorExistente.getIdTipoSanguineo().getIdTipoSanguineo());
            stmt.setString(8, dataNascimento);
            stmt.setInt(9, idDoador);

            stmt.executeUpdate();
            System.out.println("Doador atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarDoador(int idDoador) {
        Doador doadorExistente = buscarPorCodigoDoador(idDoador);
        if (doadorExistente != null && doadorExistente.getIdEndereco() != null
                && doadorExistente.getIdTipoSanguineo() != null) {
            int idEndereco = doadorExistente.getIdEndereco().getIdEndereco();
            int idTipoSanguineo = doadorExistente.getIdTipoSanguineo().getIdTipoSanguineo();
            EnderecoController enderecoController = new EnderecoController();
            TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

            String query = "DELETE FROM doador WHERE id_doador = ?";
            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(query)) {

                stmt.setInt(1, idDoador);
                int registrosAfetados = stmt.executeUpdate();

                if (registrosAfetados > 0) {
                    System.out.println("Doador deletado com sucesso!");

                    if (enderecoController.deletarEndereco(idEndereco)) {
                        System.out.println("Endereço deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o endereço.");
                    }
                    if (tipoSanguineoController.deletarTipoSanguineo(idTipoSanguineo)) {
                        System.out.println("Tipo Sanguíneo deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o tipo sanguíneo.");
                    }
                } else {
                    System.out.println("Erro ao deletar o doador. Doador não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Doador não encontrado.");
        }
    }
}
