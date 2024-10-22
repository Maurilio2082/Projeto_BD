package controller;

import conexion.DatabaseConfig;
import model.Hospital;

import model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalController {

    public List<Hospital> listarHospitais() {
        ArrayList<Hospital> hospitais = new ArrayList<>();
        EnderecoController enderecoController = new EnderecoController();
        String query = "SELECT ID_HOSPITAL,RAZAO_SOCIAL,CNPJ,EMAIL,TELEFONE,CATEGORIA,ID_ENDERECO FROM HOSPITAL";

        try (Connection conexao = DatabaseConfig.getConnection();
                Statement stmt = conexao.createStatement();
                ResultSet resultado = stmt.executeQuery(query)) {

            while (resultado.next()) {
                int idHospital = resultado.getInt("ID_HOSPITAL");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String cnpj = resultado.getString("CNPJ");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);
                Hospital hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria,
                        endereco);

                hospitais.add(hospital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospitais;
    }

    public Hospital buscarPorCodigoHospital(int codigo) {
        Hospital hospital = null;
        String query = "SELECT ID_HOSPITAL,RAZAO_SOCIAL,CNPJ,EMAIL,TELEFONE,CATEGORIA,ID_ENDERECO FROM HOSPITAL WHERE ID_HOSPITAL = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idHospital = resultado.getInt("ID_HOSPITAL");
                String razaoSocial = resultado.getString("RAZAO_SOCIAL");
                String cnpj = resultado.getString("CNPJ");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String categoria = resultado.getString("CATEGORIA");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria,
                        endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }

    public int cadastrarHospital(String logradouro, String numero, String bairro, String cidade,
            String estado, String cep, String razaoSocial, String cnpj,
            String email, String telefone, String categoria) {

        EnderecoController enderecoController = new EnderecoController();

        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);
        int idHospital = -1;

        if (idEndereco != -1) {
            String queryHospital = "INSERT INTO HOSPITAL (RAZAO_SOCIAL,CNPJ,EMAIL,TELEFONE,CATEGORIA,ID_ENDERECO) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmt = conexao.prepareStatement(queryHospital, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, razaoSocial);
                stmt.setString(2, cnpj);
                stmt.setString(3, email);
                stmt.setString(4, telefone);
                stmt.setString(5, categoria);
                stmt.setInt(6, idEndereco);
                stmt.executeUpdate();

                ResultSet registro = stmt.getGeneratedKeys();
                if (registro.next()) {
                    idHospital = registro.getInt(1);
                }

                System.out.println("Hospital cadastrado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Hospital não cadastrado.");
        }

        return idHospital;
    }

    public void atualizarHospital(int idHospital, String logradouro, String numero, String bairro,
            String cidade, String estado, String cep, String razaoSocial, String cnpj,
            String email, String telefone, String categoria) {

        EnderecoController enderecoController = new EnderecoController();
        Hospital hospitalExistente = buscarPorCodigoHospital(idHospital);

        if (hospitalExistente != null && hospitalExistente.getIdEndereco() != null) {
            int idEndereco = hospitalExistente.getIdEndereco().getIdEndereco();
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, bairro, cidade, estado, cep);
        }

        String queryHospital = "UPDATE HOSPITAL SET RAZAO_SOCIAL = ?, CNPJ = ?, EMAIL = ?, TELEFONE = ?, CATEGORIA = ? "
                +
                "WHERE ID_HOSPITAL = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(queryHospital)) {

            stmt.setString(1, razaoSocial);
            stmt.setString(2, cnpj);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setString(5, categoria);
            stmt.setInt(6, idHospital);
            stmt.executeUpdate();

            System.out.println("Hospital atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarHospital(int idHospital) {
        Hospital hospitalExistente = buscarPorCodigoHospital(idHospital);
        if (hospitalExistente != null && hospitalExistente.getIdEndereco() != null) {
            int idEndereco = hospitalExistente.getIdEndereco().getIdEndereco();
            EnderecoController enderecoController = new EnderecoController();

            String queryHospital = "DELETE FROM HOSPITAL WHERE ID_HOSPITAL = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement stmtHospital = conexao.prepareStatement(queryHospital)) {

                stmtHospital.setInt(1, idHospital);
                int registrosAfetados = stmtHospital.executeUpdate();

                if (registrosAfetados > 0) {
                    System.out.println("Hospital deletado com sucesso!");

                    if (enderecoController.deletarEndereco(idEndereco)) {
                        System.out.println("Endereço deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar o endereço.");
                    }
                } else {
                    System.out.println("Erro ao deletar o hospital. Hospital não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Hospital não encontrado ou não possui endereço associado.");
        }
    }
}
