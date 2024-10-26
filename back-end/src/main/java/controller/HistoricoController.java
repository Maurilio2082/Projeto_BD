package controller;

import conexion.DatabaseConfig;
import model.Endereco;
import model.Historico;
import model.Hospital;
import model.Paciente;
import model.Especialidade;
import model.Medico;
import java.sql.*;
import java.util.Scanner;

public class HistoricoController {
    private final Scanner scanner = new Scanner(System.in);

    public Historico buscarPorCodigoHistorico(int codigo) {
        Historico historico = null;
        HospitalController hospitalController = new HospitalController();
        PacienteController pacienteController = new PacienteController();
        EspecialidadeController especialidadeController = new EspecialidadeController();
        MedicoController medicoController = new MedicoController();

        String query = "SELECT ID_HISTORICO, DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL, ID_ESPECIALIDADE, ID_MEDICO FROM HISTORICO WHERE ID_HISTORICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int idHistorico = resultado.getInt("ID_HISTORICO");
                String dataConsulta = resultado.getString("DATA_CONSULTA");
                String observacao = resultado.getString("OBSERVACAO");
                int idPaciente = resultado.getInt("ID_PACIENTE");
                int idHospital = resultado.getInt("ID_HOSPITAL");
                int idEspecialidade = resultado.getInt("ID_ESPECIALIDADE");
                int idMedico = resultado.getInt("ID_MEDICO");

                Hospital hospital = hospitalController.buscarPorCodigoHospital(idHospital);
                Paciente paciente = pacienteController.buscarPorCodigoPaciente(idPaciente);
                Especialidade especialidade = especialidadeController.buscarPorCodigoEspecialidade(idEspecialidade);
                Medico medico = medicoController.buscarPorCodigoMedico(idMedico);

                historico = new Historico(idHistorico, dataConsulta, observacao, paciente, hospital, medico,
                        especialidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historico;
    }

    public void cadastrarHistorico() {

        System.out.println("Cadastro de Histórico:");
        System.out.print("Digite a data da consulta: ");
        String dataConsulta = scanner.nextLine();

        System.out.print("Digite a observação: ");
        String observacao = scanner.nextLine();

        System.out.print("Digite o ID do paciente (ou deixe em branco para buscar pelo CPF): ");
        String inputIdPaciente = scanner.nextLine();
        Paciente paciente = null;

        if (!inputIdPaciente.isEmpty()) {
            int idPaciente = Integer.parseInt(inputIdPaciente);
            paciente = buscarPorIdPaciente(idPaciente);
        } else {
            System.out.print("Digite o CPF do paciente: ");
            String cpfPaciente = scanner.nextLine();
            paciente = buscarPacientePorCpf(cpfPaciente);
        }

        System.out.print("Digite o ID do hospital (ou deixe em branco para buscar pelo CNPJ): ");
        String inputIdHospital = scanner.nextLine();
        Hospital hospital = null;

        if (!inputIdHospital.isEmpty()) {
            int idHospital = Integer.parseInt(inputIdHospital);
            hospital = buscarPorIdHospital(idHospital);
        } else {
            System.out.print("Digite o CNPJ do hospital: ");
            String cnpjHospital = scanner.nextLine();
            hospital = buscarHospitalPorCnpj(cnpjHospital);
        }

        System.out.print("Digite o ID da especialidade: ");
        int idEspecialidade = scanner.nextInt();
        Especialidade especialidade = new EspecialidadeController().buscarPorCodigoEspecialidade(idEspecialidade);
        if (especialidade == null) {
            System.out.println("Especialidade não encontrada.");
            return;
        }

        System.out.print("Digite o ID do médico: ");
        int idMedico = scanner.nextInt();
        Medico medico = new MedicoController().buscarPorCodigoMedico(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        if (paciente == null || hospital == null) {
            System.out.println("Paciente ou Hospital não encontrados.");
            return;
        }

        String query = "INSERT INTO HISTORICO (DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL, ID_ESPECIALIDADE, ID_MEDICO) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, dataConsulta);
            statement.setString(2, observacao);
            statement.setInt(3, paciente.getIdPaciente());
            statement.setInt(4, hospital.getIdHospital());
            statement.setInt(5, especialidade.getIdEspecialidade());
            statement.setInt(6, medico.getIdMedico());

            statement.executeUpdate();
            System.out.println("Histórico cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarHistorico() {

        System.out.print("Digite o ID do histórico para atualizar: ");
        int idHistorico = scanner.nextInt();
        scanner.nextLine();

        Historico historicoExistente = buscarPorCodigoHistorico(idHistorico);
        if (historicoExistente != null) {

            System.out.println("Deixe em branco para manter os dados atuais.");

            System.out.println("Data da consulta ( " + historicoExistente.getDataConsulta() + ")");
            String novaDataConsulta = scanner.nextLine();
            if (novaDataConsulta.isEmpty()) {
                novaDataConsulta = historicoExistente.getDataConsulta();
            }

            System.out.println("Observação (" + historicoExistente.getObservacao() + ")");
            String novaObservacao = scanner.nextLine();
            if (novaObservacao.isEmpty()) {
                novaObservacao = historicoExistente.getObservacao();
            }

            System.out
                    .println("Especialidade ( " + historicoExistente.getIdEspecialidade().getNomeEspecialidade() + ")");
            String novaEspecialidadeInput = scanner.nextLine();
            Especialidade novaEspecialidade = historicoExistente.getIdEspecialidade();
            if (!novaEspecialidadeInput.isEmpty()) {
                int novoIdEspecialidade = Integer.parseInt(novaEspecialidadeInput);
                novaEspecialidade = new EspecialidadeController().buscarPorCodigoEspecialidade(novoIdEspecialidade);
            }

            System.out.println("Medico ( " + historicoExistente.getIdMedico().getNomeMedico() + ")");
            String novoMedicoInput = scanner.nextLine();
            Medico novoMedico = historicoExistente.getIdMedico();
            if (!novoMedicoInput.isEmpty()) {
                int novoIdMedico = Integer.parseInt(novoMedicoInput);
                novoMedico = new MedicoController().buscarPorCodigoMedico(novoIdMedico);
            }

            System.out.println("Hospital ( " + historicoExistente.getIdHospital().getRazaoSocial() + ")");
            String novoHospitalInput = scanner.nextLine();
            Hospital novoHospital = historicoExistente.getIdHospital();
            if (!novoHospitalInput.isEmpty()) {
                int novoIdHospital = Integer.parseInt(novoHospitalInput);
                novoHospital = new HospitalController().buscarPorCodigoHospital(novoIdHospital);
            }

            String query = "UPDATE HISTORICO SET DATA_CONSULTA = ?, OBSERVACAO = ?, ID_ESPECIALIDADE = ?, ID_MEDICO = ?, ID_HOSPITAL= ?  WHERE ID_HISTORICO = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(query)) {

                statement.setString(1, novaDataConsulta);
                statement.setString(2, novaObservacao);
                statement.setInt(3, novaEspecialidade.getIdEspecialidade());
                statement.setInt(4, novoMedico.getIdMedico());
                statement.setInt(5, novoHospital.getIdHospital());
                statement.setInt(6, idHistorico);

                statement.executeUpdate();
                System.out.println("Historico atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deletarHistorico() {
        System.out.print("Digite o código do histórico a ser deletado: ");
        int idHistorico = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tem certeza que deseja deletar este histórico? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        String deleteHistorico = "DELETE FROM HISTORICO WHERE ID_HISTORICO = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(deleteHistorico)) {

            statement.setInt(1, idHistorico);
            int registrosAfetados = statement.executeUpdate();

            if (registrosAfetados > 0) {
                System.out.println("Histórico deletado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hospital buscarHospitalPorCnpj(String cnpj) {
        String query = "SELECT * FROM HOSPITAL WHERE CNPJ = ?";
        EnderecoController enderecoController = new EnderecoController();

        Hospital hospital = null;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, cnpj);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int idHospital = resultado.getInt("ID_HOSPITAL");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospital;
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        String query = "SELECT * FROM PACIENTE WHERE CPF = ?";
        EnderecoController enderecoController = new EnderecoController();
        Paciente paciente = null;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, cpf);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    public Paciente buscarPorIdPaciente(int id) {
        String query = "SELECT * FROM PACIENTE WHERE ID_PACIENTE = ?";
        EnderecoController enderecoController = new EnderecoController();
        Paciente paciente = null;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                paciente = new Paciente(id, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    public Hospital buscarPorIdHospital(int id) {
        String query = "SELECT * FROM HOSPITAL WHERE ID_HOSPITAL = ?";
        EnderecoController enderecoController = new EnderecoController();
        Hospital hospital = null;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String cnpj = resultado.getString("CNPJ");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                hospital = new Hospital(id, razaoSocial, cnpj, email, telefone, categoria, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }
}
