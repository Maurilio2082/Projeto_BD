package controller;

import conexion.DatabaseConfig;
import model.Medico;

import java.sql.*;
import java.util.Scanner;

public class MedicoController {

    private final Scanner scanner = new Scanner(System.in);

    public void cadastrarMedico() {

        System.out.print("Digite o nome do medico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o conselho do medico: ");
        String conselho = scanner.nextLine();

        String query = "INSERT INTO MEDICO (NOME, CONSELHO) VALUES (?, ?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, nome);
            statement.setString(2, conselho);
            statement.executeUpdate();

            ResultSet registro = statement.getGeneratedKeys();
            if (registro.next()) {
                int idMedico = registro.getInt(1);
                System.out.println("Medico cadastrado com sucesso! ID: " + idMedico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarMedico() {
        System.out.println("Remover Medico:");
        System.out.print("Digite o código do médico a ser deletado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tem certeza que deseja deletar este médico? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        RemoverDepedencia depedencia = new RemoverDepedencia();

        boolean possuiDependenciaEspecialidade = depedencia.verificarDependencia("ESPECIALIDADE_MEDICO", "ID_MEDICO",
                codigo);
        boolean possuiDependenciaHospital = depedencia.verificarDependencia("HOSPITAL_MEDICO", "ID_MEDICO", codigo);

        boolean possuiDependenciaHistorico = depedencia.verificarDependencia("HISTORICO", "ID_MEDICO", codigo);

        if (possuiDependenciaEspecialidade) {
            System.out.print("O médico possui especialidades associadas. Deseja remover esses vínculos? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }
            depedencia.deletarDependencia("ESPECIALIDADE_MEDICO", "ID_MEDICO", codigo);

        }

        if (possuiDependenciaHospital) {
            System.out.print("O médico possui hospitais associados. Deseja remover esses vínculos? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }
            depedencia.deletarDependencia("HOSPITAL_MEDICO", "ID_MEDICO", codigo);

        }

        if (possuiDependenciaHistorico) {
            System.out.print("O médico possui historico associados. Deseja remover esses vínculos? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }
            depedencia.deletarDependencia("HISTORICO", "ID_MEDICO", codigo);

        }

        String query = "DELETE FROM MEDICO WHERE ID_MEDICO = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            int registro = statement.executeUpdate();

            if (registro > 0) {
                System.out.println("Médico deletado com sucesso!");
            } else {
                System.out.println("Médico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMedico() {

        System.out.print("Digite o ID do medico para atualizar: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medicoExistente = buscarPorCodigoMedico(idMedico);

        if (medicoExistente != null) {

            System.out.println("Deixe em branco para manter os dados atuais.");

            System.out.print("Nome do Medico (" + medicoExistente.getNomeMedico() + "): ");
            String novoNome = scanner.nextLine();
            System.out.print("Numero do Conselho (" + medicoExistente.getConselho() + "): ");
            String novoConselho = scanner.nextLine();

            String query = "UPDATE MEDICO SET NOME = ?, CONSELHO = ? WHERE ID_MEDICO = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(query)) {

                statement.setString(1, novoNome.isEmpty() ? medicoExistente.getNomeMedico() : novoNome);
                statement.setString(2, novoConselho.isEmpty() ? medicoExistente.getConselho() : novoConselho);
                statement.setInt(3, idMedico);
                int registro = statement.executeUpdate();

                if (registro > 0) {
                    System.out.println("Medico atualizado com sucesso!");
                } else {
                    System.out.println("Medico com codigo " + idMedico + " nao encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
