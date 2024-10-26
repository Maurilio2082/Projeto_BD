package controller;

import conexion.DatabaseConfig;
import model.Endereco;
import model.Hospital;
import model.Medico;

import java.sql.*;
import java.util.Scanner;

public class HospitalMedicoController {

    private final Scanner scanner = new Scanner(System.in);

    public void cadastrarMedicoXHospital() {

        System.out.println("Cadastrar Médico X Hospital:");
        System.out.print("Digite o código do hospital: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine();

        Hospital hospital = buscarPorCodigoHospital(idHospital);
        if (hospital == null) {
            System.out.println("Hospital não encontrado.");
            return;
        }

        System.out.print("Digite o código do médico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarPorCodigoMedico(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        if (verificarRelacionamentoExistente(idHospital, idMedico)) {
            System.out.println("Esse relacionamento já existe entre o médico e o hospital.");
            return;
        }

        String query = "INSERT INTO HOSPITAL_MEDICO (ID_HOSPITAL, ID_MEDICO) VALUES (?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idHospital);
            statement.setInt(2, idMedico);
            statement.executeUpdate();

            System.out.println("Médico associado ao hospital com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarRelacionamentoExistente(int idHospital, int idMedico) {
        String query = "SELECT COUNT(*) FROM HOSPITAL_MEDICO WHERE ID_HOSPITAL = ? AND ID_MEDICO = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idHospital);
            statement.setInt(2, idMedico);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                return resultado.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deletarMedicoXHospital() {
        System.out.println("Remover Medico X Hospital:");

        System.out.print("Digite o código do hospital: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o código do médico a ser removido: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tem certeza que deseja remover este médico do hospital? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        String query = "DELETE FROM HOSPITAL_MEDICO WHERE ID_HOSPITAL = ? AND ID_MEDICO = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idHospital);
            statement.setInt(2, idMedico);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Médico removido do hospital com sucesso!");
            } else {
                System.out.println("Médico ou hospital não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMedicoXHospital() {
        System.out.println("Alterar Medico X Hospital:");

        System.out.print("Digite o código do hospital: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine();

        Hospital hospital = buscarPorCodigoHospital(idHospital);
        if (hospital == null) {
            System.out.println("Hospital não encontrado.");
            return;
        }

        System.out.print("Digite o código do médico atual a ser alterado: ");
        int idMedicoAtual = scanner.nextInt();
        scanner.nextLine();

        Medico medicoAtual = buscarPorCodigoMedico(idMedicoAtual);
        if (medicoAtual == null) {
            System.out.println("Médico atual não encontrado.");
            return;
        }

        System.out.print("Digite o código do novo médico: ");
        int idNovoMedico = scanner.nextInt();
        scanner.nextLine();

        Medico novoMedico = buscarPorCodigoMedico(idNovoMedico);
        if (novoMedico == null) {
            System.out.println("Novo médico não encontrado.");
            return;
        }

        String query = "UPDATE HOSPITAL_MEDICO SET ID_MEDICO = ? WHERE ID_HOSPITAL = ? AND ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, idNovoMedico);
            statement.setInt(2, idHospital);
            statement.setInt(3, idMedicoAtual);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Médico associado ao hospital atualizado com sucesso!");
            } else {
                System.out.println("Médico ou hospital não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hospital buscarPorCodigoHospital(int codigo) {
        Hospital hospital = null;
        String query = "SELECT ID_HOSPITAL, RAZAO_SOCIAL, CNPJ, EMAIL, TELEFONE, CATEGORIA, ID_ENDERECO FROM HOSPITAL WHERE ID_HOSPITAL = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String cnpj = resultado.getString("CNPJ");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                hospital = new Hospital(codigo, razaoSocial, cnpj, email, telefone, categoria, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }

    public Medico buscarPorCodigoMedico(int codigo) {
        Medico medico = null;
        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                medico = new Medico(codigo, nome, conselho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }
}
