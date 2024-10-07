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
        String query = "SELECT id_tipo_sanguineo, tipo_sangue, faltor_rh FROM tipo_sanguineo";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement tipoSanguineo = conexao.createStatement();
                ResultSet resultado = tipoSanguineo.executeQuery(query)) {

            while (resultado.next()) {
                int codigo = resultado.getInt("id_tipo_sanguineo");
                String tipoSangue = resultado.getString("tipo_sangue");
                String fatorRh = resultado.getString("faltor_rh");
                tipoSanguineos.add(new TipoSanguineo(codigo, tipoSangue, fatorRh));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSanguineos;
    }

    public TipoSanguineo buscarPorCodigTipoSanguineo(int codigo) {
        TipoSanguineo tipoSanguineos = null;
        String query = "SELECT  id_tipo_sangue, tipo_sangue,faltor_rh FROM tipo_sanguineo WHERE id_tipo_sangue = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setInt(1, codigo);
            ResultSet resultado = tipoSanguineo.executeQuery();

            if (resultado.next()) {
                int idTipoSanguineo = resultado.getInt("id_tipo_sangue");
                String tipoSangue = resultado.getString("tipo_sangue");
                String fatoRh = resultado.getString("faltor_rh");
                tipoSanguineos = new TipoSanguineo(idTipoSanguineo, tipoSangue, fatoRh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSanguineos;
    }

    public int cadastrarTipoSanguineo(String tipoSangue, String fatorRh) {
        String query = "INSERT INTO tipo_sanguineo (tipo_sangue, faltor_rh) VALUES (?,?)";
        int idTipoSanguineo = -1;
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setString(1, tipoSangue);
            tipoSanguineo.setString(2, fatorRh);
            tipoSanguineo.executeUpdate();

            ResultSet registro = tipoSanguineo.getGeneratedKeys();

            if (registro.next()) {

                idTipoSanguineo = registro.getInt(1);
            }

            System.out.println("Tipo Sanguineo cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTipoSanguineo;
    }

    public void deletarTipoSanguineo(int codigo) {
        String query = "DELETE FROM tipo_sanguineo WHERE id_tipo_sangue = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setInt(1, codigo);
            int registro = tipoSanguineo.executeUpdate();

            if (registro > 0) {
                System.out.println("Tipo Sanguineo deletado com sucesso!");
            } else {
                System.out.println("Tipo Sanguineo com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTipoSanguineo(int codigo, String tipoSangue, String fatorRh) {
        String query = "UPDATE tipo_sanguineo SET tipo_sangue = ?, fator_rh = ? WHERE id_tipo_sanguineo = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement tipoSanguineo = conexao.prepareStatement(query)) {

            tipoSanguineo.setInt(1, codigo);
            tipoSanguineo.setString(2, tipoSangue);
            tipoSanguineo.setString(3, fatorRh);
            int registro = tipoSanguineo.executeUpdate();

            if (registro > 0) {
                System.out.println("Tipo Sanguineo  atualizada com sucesso!");
            } else {
                System.out.println("Tipo Sanguineo  com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
