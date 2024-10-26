package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import conexion.DatabaseConfig;

public class RemoverDepedencia {

    public boolean verificarDependencia(String tabela, String colunaFK, int codigo) {
        String query = "SELECT COUNT(*) FROM " + tabela + " WHERE " + colunaFK + " = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deletarDependencia(String tabela, String colunaFK, int codigo) {
        String query = "DELETE FROM " + tabela + " WHERE " + colunaFK + " = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            int registrosDeletados = statement.executeUpdate();
            if (registrosDeletados > 0) {
                System.out.println("Registros dependentes deletados com sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}