package controller;

import java.util.ArrayList;
import java.util.List;
import model.Consulta;
import model.Doador;
import model.Especialidade;
import java.sql.*;
import conexion.DatabaseConfig;
import model.Hemocentro;

public class ConsultaController {

    public List<Consulta> listarConsulta() {

        ArrayList<Consulta> arrayConsultas = new ArrayList<>();
        HemocentroController hemocentroController = new HemocentroController();
        DoadorController doadorController = new DoadorController();
        EspecialidadeController especialidadeController = new EspecialidadeController();

        String query = "SELECT c.id_consulta, c.data_consulta, c.valor_credito " +
                "c.id_hemocentro,c.id_especialidade,c.id_doador " +
                "FROM consulta c";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idConsulta = resultado.getInt("id_consulta");
                String dataConsulta = resultado.getString("data_consulta");
                int valorCredito = resultado.getInt("valor_credito");
                int idHemocentro = resultado.getInt("id_hemocentro");
                int idEspecialidade = resultado.getInt("id_especialidade");
                int idDoador = resultado.getInt("id_doador");

                Hemocentro hemocentro = hemocentroController.buscarPorCodigoHemocentro(idHemocentro);
                Especialidade especialidade = especialidadeController.buscarPorCodigoEspecialidade(idEspecialidade);
                Doador doador = doadorController.buscarPorCodigoDoador(idDoador);

                Consulta consulta = new Consulta(idConsulta, dataConsulta, valorCredito);

                consulta.setIdHemocentro(hemocentro);
                consulta.setIdDoador(doador);
                consulta.setIdEspecialidade(especialidade);
                arrayConsultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayConsultas;
    }

    public Consulta buscarPorCodigoConsulta(int codigo) {
        Consulta consulta = null;
        HemocentroController hemocentroController = new HemocentroController();
        DoadorController doadorController = new DoadorController();
        EspecialidadeController especialidadeController = new EspecialidadeController();

        String query = "SELECT c.id_consulta, c.data_consulta, c.valor_credito " +
                "c.id_hemocentro,c.id_especialidade,c.id_doador " +
                "FROM consulta c" +
                "WHERE c.id_consulta = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idConsulta = resultado.getInt("id_consulta");
                String dataConsulta = resultado.getString("data_consulta");
                int valorCredito = resultado.getInt("valor_credito");
                int idHemocentro = resultado.getInt("id_hemocentro");
                int idEspecialidade = resultado.getInt("id_especialidade");
                int idDoador = resultado.getInt("id_doador");

                // Usar EnderecoController para buscar o endereço
                Hemocentro hemocentro = hemocentroController.buscarPorCodigoHemocentro(idHemocentro);
                Especialidade especialidade = especialidadeController.buscarPorCodigoEspecialidade(idEspecialidade);
                Doador doador = doadorController.buscarPorCodigoDoador(idDoador);

                consulta = new Consulta(idConsulta, dataConsulta, valorCredito);
                consulta.setIdHemocentro(hemocentro);
                consulta.setIdDoador(doador);
                consulta.setIdEspecialidade(especialidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consulta;
    }

    // Cadastrar doador e usar EnderecoController para manipular endereços
    public void cadastrarConsulta(String logradouroDoa, String numeroDoa, String complementoDoa, String bairroDoa,
            String cidadeDoa,
            String estadoDoa, String cepDoa,
            String nome, String cpf, String emailDoa, String telefoneDoa,
            String tipoSanguineo, String dataNascimento, String tipoSangue, String fatorRh, String nomeEspecialidade,
            String logradouroHemo, String numeroHemo, String complementoHemo, String bairroHemo, String cidadeHemo,
            String estado,
            String cepHemo, String razaoSocial, String cnpj, String emailHemo, String telefoneHemo,
            String dataConsulta, String peso, String quantidadeDoacoes) {

        HemocentroController hemocentroController = new HemocentroController();
        DoadorController doadorController = new DoadorController();
        EspecialidadeController especialidadeController = new EspecialidadeController();

        int idHemocentro = hemocentroController.cadastrarHemocentro(logradouroHemo, numeroHemo, complementoHemo,
                bairroHemo, cidadeHemo, estado, cepHemo, razaoSocial, cnpj, emailHemo, telefoneHemo);

        int idEspecialidade = especialidadeController.cadastrarEspecialidade(nomeEspecialidade);

        int idDoador = doadorController.cadastrarDoador(logradouroDoa, numeroDoa, complementoDoa, bairroDoa,
                cidadeDoa, estado, cepDoa, nomeEspecialidade, cpf, emailDoa, telefoneDoa, peso, quantidadeDoacoes,
                dataNascimento, tipoSangue, fatorRh);

        if (idHemocentro != -1 && idDoador != -1 && idEspecialidade != -1) {
            String queryDoador = "INSERT INTO consulta (data_consulta,valor_credito, id_hemocentro, id_doador,id_especialidade) VALUES (?,?,?,?,?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryDoador, Statement.RETURN_GENERATED_KEYS)) {

                // Inserir o doador com o id_endereco recuperado
                stmt.setString(1, dataConsulta);
                stmt.setInt(2, idHemocentro);
                stmt.setInt(3, idDoador);
                stmt.setInt(4, idEspecialidade);

                stmt.executeUpdate();
                System.out.println("Banco de Sangue cadastrado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Doador não cadastrado.");
        }
    }

    // Atualizar doador e usar EnderecoController para manipular endereços
    public void atualizarConsulta(int idConsulta, String logradouroDoa, String numeroDoa, String complementoDoa,
            String bairroDoa,
            String cidadeDoa, String estadoDoa, String cepDoa, String nome, String cpf, String emailDoa,
            String telefoneDoa,
            String tipoSanguineo, String dataNascimento, String tipoSangue, String fatorRh, String nomeEspecialidade,
            String logradouroHemo, String numeroHemo, String complementoHemo, String bairroHemo, String cidadeHemo,
            String estadoHemo, String cepHemo, String razaoSocial, String cnpj, String emailHemo, String telefoneHemo,
            String dataConsulta, String peso, String quantidadeDoacoes) {

        HemocentroController hemocentroController = new HemocentroController();
        DoadorController doadorController = new DoadorController();
        EspecialidadeController especialidadeController = new EspecialidadeController();

        // Buscar a consulta existente
        Consulta consultaExistente = buscarPorCodigoConsulta(idConsulta);
        if (consultaExistente != null) {
            // Atualizar Hemocentro
            int idHemocentro = consultaExistente.getIdHemocentro().getIdHemocentro();
            hemocentroController.atualizarHemocentro(idHemocentro, logradouroHemo, numeroHemo, complementoHemo,
                    bairroHemo, cidadeHemo, estadoHemo, cepHemo, razaoSocial, cnpj, emailHemo, telefoneHemo);

            // Atualizar Doador
            int idDoador = consultaExistente.getIdDoador().getIdDoador();
            doadorController.atualizarDoador(idDoador, logradouroDoa, numeroDoa, complementoDoa, bairroDoa,
                    cidadeDoa, estadoDoa, cepDoa, nome, cpf, emailDoa, telefoneDoa, peso, quantidadeDoacoes,
                    dataNascimento,
                    tipoSangue, fatorRh);

            // Atualizar Especialidade
            int idEspecialidade = consultaExistente.getIdEspecialidade().getIdEspecialidade();
            especialidadeController.atualizarEspecialidade(idEspecialidade, nomeEspecialidade);

            // Atualizar os dados da consulta
            String queryAtualizarConsulta = "UPDATE consulta SET data_consulta = ?, id_hemocentro = ?, id_doador = ?, id_especialidade = ? WHERE id_consulta = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryAtualizarConsulta)) {

                stmt.setString(1, dataConsulta);
                stmt.setInt(2, idHemocentro);
                stmt.setInt(3, idDoador);
                stmt.setInt(4, idEspecialidade);
                stmt.setInt(5, idConsulta);

                stmt.executeUpdate();
                System.out.println("Consulta atualizada com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    public void deletarConsulta(int idConsulta) {
        Consulta consultaExistente = buscarPorCodigoConsulta(idConsulta);
        if (consultaExistente != null && consultaExistente.getIdHemocentro() != null
                && consultaExistente.getIdDoador() != null && consultaExistente.getIdEspecialidade() != null) {

            int idHemocentro = consultaExistente.getIdHemocentro().getIdHemocentro();
            int idDoador = consultaExistente.getIdDoador().getIdDoador();
            int idEspecialidade = consultaExistente.getIdEspecialidade().getIdEspecialidade();

            HemocentroController hemocentroController = new HemocentroController();
            DoadorController doadorController = new DoadorController();
            EspecialidadeController especialidadeController = new EspecialidadeController();

            // Deletar o doador, hemocentro e especialidade relacionados à consulta
            doadorController.deletarDoador(idDoador);
            hemocentroController.deletarHemocentro(idHemocentro);
            especialidadeController.deletarEspecialidade(idEspecialidade);
        }

        // Deletar a consulta
        String query = "DELETE FROM consulta WHERE id_consulta = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, idConsulta);
            stmt.executeUpdate();
            System.out.println("Consulta deletada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
