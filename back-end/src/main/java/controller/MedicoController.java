package controller;

import conexion.DatabaseConfig;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto especialidade.
 * ##########################################################################
 */

public class MedicoController {

    public List<Medico> listarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement medico = conexao.createStatement();
                ResultSet resultado = medico.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("ID_MEDICO");
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                medicos.add(new Medico(codigo, nome, conselho));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public Medico buscarPorCodigoMedico(int codigo) {
        Medico medicos = null;

        String query = "SELECT ID_MEDICO, NOME, CONSELHO FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement medico = conexao.prepareStatement(query)) {

            medico.setInt(1, codigo);
            ResultSet resultado = medico.executeQuery();

            if (resultado.next()) {
                codigo = resultado.getInt("ID_MEDICO");
                String nome = resultado.getString("NOME");
                String conselho = resultado.getString("CONSELHO");
                medicos = new Medico(codigo, nome, conselho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public int cadastrarMedico(String nome, String conselho) {
        String query = "INSERT INTO MEDICO (NOME,CONSELHO) VALUES (?,?)";
        int idMedico = -1;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement medico = conexao.prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            medico.setString(1, nome);
            medico.setString(2, conselho);
            medico.executeUpdate();

            ResultSet registro = medico.getGeneratedKeys();
            if (registro.next()) {
                idMedico = registro.getInt(1);
            }

            System.out.println("Medico cadastrada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMedico;
    }

    public void deletarMedico(int codigo) {
        String query = "DELETE FROM MEDICO WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement medico = conexao.prepareStatement(query)) {

            medico.setInt(1, codigo);
            int registro = medico.executeUpdate();

            if (registro > 0) {
                System.out.println("Medico deletada com sucesso!");
            } else {
                System.out.println("Medico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMedico(int codigo, String novoNome, String conselho) {
        String query = "UPDATE MEDICO SET NOME = ?, CONSELHO = ? WHERE ID_MEDICO = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement medico = conexao.prepareStatement(query)) {

            medico.setString(1, novoNome);
            medico.setString(2, conselho);
            medico.setInt(3, codigo);
            int registro = medico.executeUpdate();

            if (registro > 0) {
                System.out.println("Medico atualizada com sucesso!");
            } else {
                System.out.println("Medico com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
