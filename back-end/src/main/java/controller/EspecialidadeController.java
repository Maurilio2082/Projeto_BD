package controller;

import conexion.DatabaseConfig;
import model.Especialidade;
import java.util.Scanner;
import java.sql.*;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto especialidade.
 * ##########################################################################
 */

public class EspecialidadeController {

    private final Scanner scanner = new Scanner(System.in);

    public Especialidade buscarPorCodigoEspecialidade(int codigo) {
        Especialidade especialidade = null;
        String query = "SELECT id_especialidade, nome_especialidade FROM especialidade WHERE id_especialidade = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome_especialidade");
                int idEspecialidade = resultado.getInt("id_especialidade");
                especialidade = new Especialidade(idEspecialidade, nome);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidade;
    }

    public void cadastrarEspecialidade() {
        System.out.println("Cadastro de Especialidade:");
        System.out.print("Digite o nome da especialidade: ");
        String nome = scanner.nextLine();

        String query = "INSERT INTO especialidade (nome_especialidade) VALUES (?)";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, nome);
            statement.executeUpdate();

            ResultSet registro = statement.getGeneratedKeys();
            if (registro.next()) {
                int idEspecialidade = registro.getInt(1);
                System.out.println("Especialidade cadastrada com sucesso! ID: " + idEspecialidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEspecialidade() {
        System.out.println("Deletar Especialidade:");
        System.out.print("Digite o código da especialidade a ser deletada: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tem certeza que deseja deletar a especialidade? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        RemoverDepedencia depedencia = new RemoverDepedencia();

        boolean possuiDependencia = depedencia.verificarDependencia("ESPECIALIDADE_MEDICO", "ID_ESPECIALIDADE", codigo);
        if (possuiDependencia) {
            System.out.print("A especialidade possui vínculos. Deseja deletar esses registros vinculados? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }

            depedencia.deletarDependencia("ESPECIALIDADE_MEDICO", "ID_ESPECIALIDADE", codigo);
        }

        String query = "DELETE FROM especialidade WHERE id_especialidade = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            int registro = statement.executeUpdate();

            if (registro > 0) {
                System.out.println("Especialidade deletada com sucesso!");
            } else {
                System.out.println("Especialidade com código " + codigo + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEspecialidade() {
        System.out.print("Digite o ID da especialidade para atualizar: ");
        int idEspecialidade = scanner.nextInt();
        scanner.nextLine();

        Especialidade especialidadeExistente = buscarPorCodigoEspecialidade(idEspecialidade);
        if (especialidadeExistente != null) {
            System.out.println("Deixe em branco para manter os dados atuais.");

            System.out.print("Nome da Especialidade (" + especialidadeExistente.getNomeEspecialidade() + "): ");
            String nome = scanner.nextLine();

            String queryEspecialidade = "UPDATE ESPECIALIDADE SET NOME = ? WHERE ID_ESPECIALIDADE = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(queryEspecialidade)) {

                statement.setString(1, nome.isEmpty() ? especialidadeExistente.getNomeEspecialidade() : nome);
                statement.setInt(2, idEspecialidade);

                int registro = statement.executeUpdate();

                if (registro > 0) {
                    System.out.println("Especialidade atualizado com sucesso!");
                } else {
                    System.out.println("Especialidade com código " + idEspecialidade + " não encontrado.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Especialidade nao encontrada.");
        }
    }
}