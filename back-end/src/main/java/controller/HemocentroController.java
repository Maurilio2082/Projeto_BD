package controller;

import conexion.DatabaseConfig;
import model.Hemocentro;
import model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ##########################################################################
 * # Classe usada para criar os metodos que gerenciam o objeto hemocentro.
 * ##########################################################################
 */

public class HemocentroController {

    public List<Hemocentro> listarHemocentros() {
        ArrayList<Hemocentro> hemocentros = new ArrayList<>();
        EnderecoController enderecoController = new EnderecoController();
        String query = "SELECT h.id_hemocentro, h.razao_social, h.cnpj, h.email, h.telefone, "
                +
                "h.id_endereco " +
                "FROM hemocentro h";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idHemocentro = resultado.getInt("id_hemocentro");
                String razaoSocial = resultado.getString("razao_social");
                String cnpj = resultado.getString("cnpj");
                String email = resultado.getString("email");
                String telefone = resultado.getString("telefone");
                int idEndereco = resultado.getInt("id_endereco");

                // Usar EnderecoController para buscar o endereço
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                Hemocentro hemocentro = new Hemocentro(idHemocentro, razaoSocial, cnpj, email, telefone, endereco);

                hemocentros.add(hemocentro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hemocentros;
    }

    public Hemocentro buscarPorCodigoHemocentro(int codigo) {
        Hemocentro hemocentro = null;
        String query = "SELECT h.id_hemocentro, h.razao_social, h.cnpj, h.email, h.telefone, h.id_endereco " +
                "FROM hemocentro h WHERE h.id_hemocentro = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idHemocentro = resultado.getInt("id_hemocentro");
                String razaoSocial = resultado.getString("razao_social");
                String cnpj = resultado.getString("cnpj");
                String email = resultado.getString("email");
                String telefone = resultado.getString("telefone");
                int idEndereco = resultado.getInt("id_endereco");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                hemocentro = new Hemocentro(idHemocentro, razaoSocial, cnpj, email, telefone, endereco);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hemocentro;
    }

    public int cadastrarHemocentro(String logradouro, String numero, String complemento, String bairro, String cidade,
            String estado,
            String cep, String razaoSocial, String cnpj, String email, String telefone) {

        EnderecoController enderecoController = new EnderecoController();

        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, complemento, bairro, cidade, estado,
                cep);
        int idHemocentro = -1;

        if (idEndereco != -1) {

            String queryDoador = "INSERT INTO hemocentro (razao_social, cnpj, email, telefone, id_endereco) VALUES (?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement hemocentro = conexao.prepareStatement(queryDoador,
                            Statement.RETURN_GENERATED_KEYS)) {

                
                hemocentro.setString(1, razaoSocial);
                hemocentro.setString(2, cnpj);
                hemocentro.setString(3, email);
                hemocentro.setString(4, telefone);
                hemocentro.setInt(5, idEndereco);
                hemocentro.executeUpdate();

                ResultSet registro = hemocentro.getGeneratedKeys();

                if (registro.next()) {
                    idHemocentro = registro.getInt(1);
                }

                System.out.println("Hemocentro cadastrada com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Hemocentro não cadastrado.");
        }
        return idHemocentro;

    }

    public void atualizarHemocentro(int idHemocentro, String logradouro, String numero, String complemento,
            String bairro, String cidade,
            String estado, String cep, String razaoSocial, String cnpj, String email,
            String telefone) {

        EnderecoController enderecoController = new EnderecoController();

        // Atualizar o endereço
        Hemocentro hemocentroExistente = buscarPorCodigoHemocentro(idHemocentro);
        if (hemocentroExistente != null && hemocentroExistente.getIdEndereco() != null) {
            int idEndereco = hemocentroExistente.getIdEndereco().getIdEndereco();
            
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, complemento, bairro, cidade, estado,
                    cep);
        }

        String queryDoador = "UPDATE hemocentro SET razao_social = ?, cnpj = ?, email = ?, telefone = ? WHERE id_hemocentro = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryDoador)) {

            stmt.setString(1, razaoSocial);
            stmt.setString(2, cnpj);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setInt(5, idHemocentro);

            stmt.executeUpdate();
            System.out.println("Hemocentro atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarHemocentro(int idHemocentro) {
        Hemocentro hemocentroExistente = buscarPorCodigoHemocentro(idHemocentro);
        if (hemocentroExistente != null && hemocentroExistente.getIdEndereco() != null) {
            int idEndereco = hemocentroExistente.getIdEndereco().getIdEndereco();
            EnderecoController enderecoController = new EnderecoController();

            // Primeiro, tenta excluir o hemocentro
            String queryHemocentro = "DELETE FROM hemocentro WHERE id_hemocentro = ?";
            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmtHemocentro = conexao.prepareStatement(queryHemocentro)) {

                stmtHemocentro.setInt(1, idHemocentro);
                int registrosAfetados = stmtHemocentro.executeUpdate();

                if (registrosAfetados > 0) {
                    System.out.println("Hemocentro deletado com sucesso!");

                    // Após deletar o hemocentro, tenta deletar o endereço
                    if (enderecoController.deletarEndereco(idEndereco)) {
                        System.out.println("Endereço deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o endereço.");
                    }
                } else {
                    System.out.println("Erro ao deletar o hemocentro. Hemocentro não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Hemocentro não encontrado ou não possui endereço associado.");
        }
    }

}
