package controller;

import conexion.DatabaseConfig;
import model.Hospital;
import model.Endereco;

import java.sql.*;
import java.util.Scanner;

public class HospitalController {

    // Método para listar hospitais com interação do usuário
    public void listarHospitais() {
        EnderecoController enderecoController = new EnderecoController();
        String query = "SELECT ID_HOSPITAL, RAZAO_SOCIAL, CNPJ, EMAIL, TELEFONE, CATEGORIA, ID_ENDERECO FROM HOSPITAL";

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
                Hospital hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria, endereco);

                System.out.println(hospital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um hospital por código com interação do usuário
    public void buscarPorCodigoHospital() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o código do hospital: ");
        int codigo = scanner.nextInt();

        String query = "SELECT ID_HOSPITAL, RAZAO_SOCIAL, CNPJ, EMAIL, TELEFONE, CATEGORIA, ID_ENDERECO FROM HOSPITAL WHERE ID_HOSPITAL = ?";

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

                Hospital hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria, endereco);
                System.out.println(hospital);
            } else {
                System.out.println("Hospital com código " + codigo + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar hospital com interação do usuário
    public void cadastrarHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Digite a razão social: ");
        String razaoSocial = scanner.nextLine();

        System.out.print("Digite o CNPJ: ");
        String cnpj = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite a categoria: ");
        String categoria = scanner.nextLine();

        EnderecoController enderecoController = new EnderecoController();
        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);

        if (idEndereco != -1) {
            String queryHospital = "INSERT INTO HOSPITAL (RAZAO_SOCIAL, CNPJ, EMAIL, TELEFONE, CATEGORIA, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)";

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
                    int idHospital = registro.getInt(1);
                    System.out.println("Hospital cadastrado com sucesso! ID: " + idHospital);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Hospital não cadastrado.");
        }
    }

    // Método para atualizar hospital com interação do usuário
    public void atualizarHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do hospital a ser atualizado: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        System.out.print("Digite o logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Digite a razão social: ");
        String razaoSocial = scanner.nextLine();

        System.out.print("Digite o CNPJ: ");
        String cnpj = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite a categoria: ");
        String categoria = scanner.nextLine();

        EnderecoController enderecoController = new EnderecoController();
        Hospital hospitalExistente = buscarPorCodigoHospital(idHospital);

        if (hospitalExistente != null && hospitalExistente.getIdEndereco() != null) {
            int idEndereco = hospitalExistente.getIdEndereco().getIdEndereco();
            enderecoController.atualizarEndereco(idEndereco, logradouro, numero, bairro, cidade, estado, cep);
        }

        String queryHospital = "UPDATE HOSPITAL SET RAZAO_SOCIAL = ?, CNPJ = ?, EMAIL = ?, TELEFONE = ?, CATEGORIA = ? WHERE ID_HOSPITAL = ?";

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

    // Método para deletar hospital com interação do usuário
    public void deletarHospital() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do hospital a ser deletado: ");
        int idHospital = scanner.nextInt();

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
