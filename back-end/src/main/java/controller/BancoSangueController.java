package controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import conexion.DatabaseConfig;
import model.Hemocentro;
import model.TipoSanguineo;
import model.BancoSangue;

public class BancoSangueController {
    public List<BancoSangue> listarBancoSangue() {
        ArrayList<BancoSangue> bancoSangues = new ArrayList<>();
        HemocentroController hemocentroController = new HemocentroController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        String query = "SELECT b.id_banco_sangue, b.quantidade_disponivel, " +
                "b.id_hemocentro,b.id_tipo_sanguineo " +
                "FROM banco_sangue b";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idBancoSangue = resultado.getInt("id_banco_sangue");
                String quantidadeDisponivel = resultado.getString("quantidade_disponivel");
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");
                int idHemocentro = resultado.getInt("id_hemocentro");

                // Usar EnderecoController para buscar o endereço
                Hemocentro hemocentro = hemocentroController.buscarPorCodigoHemocentro(idHemocentro);
                TipoSanguineo tipoSanguineo = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);
                BancoSangue bancoSangue = new BancoSangue(idBancoSangue, quantidadeDisponivel);

                bancoSangue.setIdHemocentro(hemocentro);
                bancoSangue.setIdTipoSanguineo(tipoSanguineo);
                bancoSangues.add(bancoSangue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bancoSangues;
    }

    public BancoSangue buscarPorCodigoBancoSangue(int codigo) {
        BancoSangue bancoSangue = null;
        HemocentroController hemocentroController = new HemocentroController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        String query = "SELECT b.id_banco_sangue, b.quantidade_disponivel, " +
                "b.id_hemocentro,b.id_tipo_sanguineo  " +
                "FROM banco_sangue b " +
                "WHERE b.id_banco_sangue = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idDoador = resultado.getInt("id_banco_sangue");
                String nome = resultado.getString("quantidade_disponivel");
                int idHemocentro = resultado.getInt("id_hemocentro");
                int idTipoSanguineo = resultado.getInt("id_tipo_sanguineo");

                // Usar EnderecoController para buscar o endereço
                Hemocentro hemocentro = hemocentroController.buscarPorCodigoHemocentro(idHemocentro);
                TipoSanguineo tipoSangue = tipoSanguineoController.buscarPorCodigTipoSanguineo(idTipoSanguineo);
                bancoSangue = new BancoSangue(idDoador, nome);
                bancoSangue.setIdHemocentro(hemocentro);
                bancoSangue.setIdTipoSanguineo(tipoSangue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bancoSangue;
    }

    // Cadastrar doador e usar EnderecoController para manipular endereços
    public void cadastrarBancoSangue(String logradouro, String numero, String complemento, String bairro, String cidade,
            String estado,
            String cep, String razaoSocial, String cnpj, String email, String telefone, String tipoSangue,
            String fatorRh, String quantidadeDisponivel) {

        HemocentroController hemocentroController = new HemocentroController();
        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

        int idHemocentro = hemocentroController.cadastrarHemocentro(logradouro, numero, complemento, bairro, cidade,
                estado, cep, razaoSocial, cnpj, email, telefone);

        int idTipoSanguineo = tipoSanguineoController.cadastrarTipoSanguineo(tipoSangue, fatorRh);

        if (idHemocentro != -1 && idTipoSanguineo != -1) {
            String queryDoador = "INSERT INTO banco_sangue (quantidade_disponivel, id_hemocentro, id_endereco) VALUES (?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

                // Inserir o doador com o id_endereco recuperado
                stmt.setString(1, quantidadeDisponivel);
                stmt.setInt(2, idHemocentro);
                stmt.setInt(3, idTipoSanguineo);

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
    public void atualizarBancoSangue(int idBancoSangue, String logradouro, String numero, String complemento,
            String bairro, String cidade,
            String estado,
            String cep, String razaoSocial, String cnpj, String email, String telefone, String tipoSangue,
            String fatorRh, String quantidadeDisponivel) {

        TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();
        HemocentroController hemocentroController = new HemocentroController();

        // Atualizar o endereço e tipo sanguineo
        BancoSangue bancoSangueExistente = buscarPorCodigoBancoSangue(idBancoSangue);
        if (bancoSangueExistente != null && bancoSangueExistente.getIdTipoSanguineo() != null
                && bancoSangueExistente.getIdHemocentro() != null) {

            int idHemocentro = bancoSangueExistente.getIdHemocentro().getIdHemocentro();
            int idTipoSanguineo = bancoSangueExistente.getIdTipoSanguineo().getIdTipoSanguineo();

            tipoSanguineoController.atualizarTipoSanguineo(idTipoSanguineo, tipoSangue, fatorRh);

            hemocentroController.atualizarHemocentro(idHemocentro, logradouro, numero, complemento, bairro, cidade,
                    estado, cep, razaoSocial, cnpj, email, telefone);

        }

        // Atualizar o doador
        String queryDoador = "UPDATE banco_sangue SET quantidade_disponivel = ?" +
                " WHERE id_banco_sangue = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

            stmt.setString(1, quantidadeDisponivel);

            stmt.executeUpdate();
            System.out.println("Banco de Sangue atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarBancoSangue(int idBancoSangue) {
        BancoSangue bancoSangueExistente = buscarPorCodigoBancoSangue(idBancoSangue);
        if (bancoSangueExistente != null && bancoSangueExistente.getIdHemocentro() != null
                && bancoSangueExistente.getIdTipoSanguineo() != null) {

            int idTipoSanguineo = bancoSangueExistente.getIdTipoSanguineo().getIdTipoSanguineo();
            int idHemocentro = bancoSangueExistente.getIdHemocentro().getIdHemocentro();

            HemocentroController HemocentroController = new HemocentroController();
            TipoSanguineoController tipoSanguineoController = new TipoSanguineoController();

            tipoSanguineoController.deletarTipoSanguineo(idTipoSanguineo);
            HemocentroController.deletarHemocentro(idHemocentro);
            ;
        }

        String query = "DELETE FROM banco_sangue WHERE id_banco_sangue = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, idBancoSangue);
            stmt.executeUpdate();
            System.out.println("Banco de Sangue deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
