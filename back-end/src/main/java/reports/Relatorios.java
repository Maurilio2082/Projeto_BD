package reports;

import conexion.DatabaseConfig;
import controller.*;
import model.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Relatorios {

    private String queryRelatorioEspecialidades;
    private String queryRelatorioHospitais;
    private String queryRelatorioPacientes;
    private String queryRelatorioMedicos;
    private String queryRelatorioHistoricos;
    private String queryRelatorioEspecialidadeMedicos;
    private String queryRelatorioHospitaisMedicos;
    private String queryRelatorioAgrupamentoEsp;

    public Relatorios() {
        queryRelatorioEspecialidades = lerArquivoSQL("sql/listar_especialidades.sql");
        queryRelatorioHospitais = lerArquivoSQL("sql/listar_hospitais.sql");
        queryRelatorioPacientes = lerArquivoSQL("sql/listar_pacientes.sql");
        queryRelatorioMedicos = lerArquivoSQL("sql/listar_medicos.sql");
        queryRelatorioHistoricos = lerArquivoSQL("sql/listar_historicos.sql");
        queryRelatorioEspecialidadeMedicos = lerArquivoSQL("sql/listar_medicos_especialidade.sql");
        queryRelatorioHospitaisMedicos = lerArquivoSQL("sql/listar_medicos_hospitais.sql");
        queryRelatorioAgrupamentoEsp = lerArquivoSQL("sql/listar_agrupamento_especialidade.sql");
    }

    private String lerArquivoSQL(String caminhoArquivo) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(caminhoArquivo);
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
                }
            } else {
                System.err.println("Arquivo SQL não encontrado: " + caminhoArquivo);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo SQL: " + caminhoArquivo + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<String[]> getRelatorioAgrupamentoEsp() {
        List<String[]> relatorio = new ArrayList<>();
        if (queryRelatorioAgrupamentoEsp == null) {
            System.err.println("A consulta SQL está nula.");
            return relatorio;
        }

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioAgrupamentoEsp)) {

            while (resultado.next()) {
                String nomeEspecialidade = resultado.getString("NOME_ESPECIALIDADE");
                String qtsEspecialidade = resultado.getString("QUANTIDADE_MEDICOS");
                relatorio.add(new String[] { nomeEspecialidade, qtsEspecialidade });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorio;
    }

    public List<Especialidade> getRelatorioEspecialidade() {
        List<Especialidade> especialidades = new ArrayList<>();
        if (queryRelatorioEspecialidades == null) {
            System.err.println("A consulta SQL está nula.");
            return especialidades;
        }

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement especialidade = conexao.createStatement();
                ResultSet resultado = especialidade.executeQuery(queryRelatorioEspecialidades)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("ID_ESPECIALIDADE");
                String nome = resultado.getString("NOME_ESPECIALIDADE");
                especialidades.add(new Especialidade(codigo, nome));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public List<Medico> getRelatorioMedicos() {
        List<Medico> medicos = new ArrayList<>();
        if (queryRelatorioMedicos == null) {
            System.err.println("A consulta SQL está nula.");
            return medicos;
        }

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryRelatorioMedicos)) {

            while (rs.next()) {
                int id = rs.getInt("ID_MEDICO");
                String nome = rs.getString("NOME");
                String conselho = rs.getString("CONSELHO");
                medicos.add(new Medico(id, nome, conselho));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public List<Hospital> getRelatorioHospitais() {
        ArrayList<Hospital> hospitais = new ArrayList<>();
        if (queryRelatorioHospitais == null) {
            System.err.println("A consulta SQL está nula.");
            return hospitais;
        }
        EnderecoController enderecoController = new EnderecoController();

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioHospitais)) {

            while (resultado.next()) {
                int idHospital = resultado.getInt("ID_HOSPITAL");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String cnpj = resultado.getString("CNPJ");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("e.ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                Hospital hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria,
                        endereco);

                hospitais.add(hospital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospitais;
    }

    public List<Paciente> getRelatorioPacientes() {
        ArrayList<Paciente> pacientes = new ArrayList<>();

        if (queryRelatorioPacientes == null) {
            System.err.println("A consulta SQL está nula.");
            return pacientes;
        }
        EnderecoController enderecoController = new EnderecoController();

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioPacientes)) {

            while (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("e.ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                Paciente paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf,
                        endereco);

                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    public List<Historico> getRelatorioHistoricos() {
        ArrayList<Historico> historicos = new ArrayList<>();
        PacienteController pacienteController = new PacienteController();
        HospitalController hospitalController = new HospitalController();
        EspecialidadeController especialidadeController = new EspecialidadeController();
        MedicoController medicoController = new MedicoController();

        if (queryRelatorioHistoricos == null) {
            System.err.println("A consulta SQL está nula.");
            return historicos;
        }

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioHistoricos)) {

            while (resultado.next()) {
                int idHistorico = resultado.getInt("ID_HISTORICO");
                String dataConsulta = resultado.getString("DATA_CONSULTA");
                String observacao = resultado.getString("OBSERVACAO");
                int idPaciente = resultado.getInt("h.ID_PACIENTE");
                int idHospital = resultado.getInt("h.ID_HOSPITAL");
                int idEspecialidade = resultado.getInt("h.ID_ESPECIALIDADE");
                int idMedico = resultado.getInt("h.ID_MEDICO");

                Paciente paciente = pacienteController.buscarPorCodigoPaciente(idPaciente);
                Hospital hospital = hospitalController.buscarPorCodigoHospital(idHospital);
                Especialidade especialidade = especialidadeController.buscarPorCodigoEspecialidade(idEspecialidade);
                Medico medico = medicoController.buscarPorCodigoMedico(idMedico);

                Historico historico = new Historico(idHistorico, dataConsulta, observacao, paciente, hospital, medico,
                        especialidade);

                historicos.add(historico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historicos;
    }

    public List<String[]> getRelatorioEspecialidadeMedicos() {
        List<String[]> relatorio = new ArrayList<>();
        if (queryRelatorioEspecialidadeMedicos == null) {
            System.err.println("A consulta SQL está nula.");
            return relatorio;
        }

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioEspecialidadeMedicos)) {

            while (resultado.next()) {
                String nomeMedico = resultado.getString("NOME");
                String nomeEspecialidade = resultado.getString("NOME_ESPECIALIDADE");
                relatorio.add(new String[] { nomeMedico, nomeEspecialidade });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorio;
    }

    public List<String[]> getRelatorioHospitalMedicos() {
        List<String[]> relatorio = new ArrayList<>();
        if (queryRelatorioHospitaisMedicos == null) {
            System.err.println("A consulta SQL está nula.");
            return relatorio;
        }

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(queryRelatorioHospitaisMedicos)) {

            while (resultado.next()) {
                String nomeMedico = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String categoria = resultado.getString("CATEGORIA");
                String nomeEspecialidade = resultado.getString("NOME_ESPECIALIDADE");
                relatorio.add(new String[] { nomeMedico, conselho, razaoSocial, categoria, nomeEspecialidade });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorio;
    }

}
