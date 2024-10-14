package controller;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.TipoSanguineo;
import java.sql.*;

public class TipoSanguineoController {

    /*
     * ##########################################################################
     * # Classe usada para criar os metodos que gerenciam o objeto tipo sanguineo.
     * ##########################################################################
     */

    public List<TipoSanguineo> listarTipoSanguineos() {
        List<TipoSanguineo> tipoSanguineos = new ArrayList<>();
        String query = "SELECT id_tipo_sanguineo, tipo_sangue, fator_rh FROM tipo_sanguineo";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement tipoSanguineo = conexao.createStatement();
                ResultSet resultado = tipoSanguineo.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("id_tipo_sanguineo");
                String tipoSangue = resultado.getString("tipo_sangue");
                String fatorRh = resultado.getString("fator_rh");
                tipoSanguineos.add(new TipoSanguineo(codigo, tipoSangue, fatorRh));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSanguineos;
    }

    public TipoSanguineo buscarPorCodigTipoSanguineo(int codigo) {
        TipoSanguineo tipoSanguineos = null;
        String query = "SELECT  id_tipo_sanguineo, tipo_sangue,fator_rh FROM tipo_sanguineo WHERE id_tipo_sanguineo = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setInt(1, codigo);
            ResultSet resultado = tipoSanguineo.executeQuery();

            if (resultado.next()) {
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");
                String tipoSangue = resultado.getString("tipo_sangue");
                String fatoRh = resultado.getString("fator_rh");
                tipoSanguineos = new TipoSanguineo(idTipoSanguineo, tipoSangue, fatoRh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSanguineos;
    }

    public int cadastrarTipoSanguineo(String tipoSangue, String fatorRh) {
        String query = "INSERT INTO tipo_sanguineo (tipo_sangue, fator_rh) VALUES (?,?)";
        int idTipoSanguineo = -1;

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            tipoSanguineo.setString(1, tipoSangue);
            tipoSanguineo.setString(2, fatorRh);
            tipoSanguineo.executeUpdate();

            ResultSet registro = tipoSanguineo.getGeneratedKeys(); // Obtém as chaves geradas

            if (registro.next()) {
                idTipoSanguineo = registro.getInt(1);
            }

            System.out.println("Tipo Sanguíneo cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTipoSanguineo;
    }

    public boolean deletarTipoSanguineo(int codigo) {
        String query = "DELETE FROM tipo_sanguineo WHERE id_tipo_sanguineo = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setInt(1, codigo);
            int registro = tipoSanguineo.executeUpdate();

            return registro > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void atualizarTipoSanguineo(int idTipoSanguineo, String tipoSangue, String fatorRh) {
        // Verificação dos valores
        System.out.println("Atualizando Tipo Sanguíneo:");
        System.out.println("ID: " + idTipoSanguineo);
        System.out.println("Tipo Sangue: " + tipoSangue);
        System.out.println("Fator Rh: " + fatorRh);

        String query = "UPDATE tipo_sanguineo SET tipo_sangue = ?, fator_rh = ? WHERE id_tipo_sanguineo = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setString(1, tipoSangue);
            tipoSanguineo.setString(2, fatorRh); // Certifique-se de que isso é um valor válido
            tipoSanguineo.setInt(3, idTipoSanguineo);

            int registro = tipoSanguineo.executeUpdate();

            if (registro > 0) {
                System.out.println("Tipo Sanguíneo atualizado com sucesso!");
            } else {
                System.out.println("Tipo Sanguíneo com código " + idTipoSanguineo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
