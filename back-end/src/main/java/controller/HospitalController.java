package controller;

import conexion.DatabaseConfig;
import model.Hospital;

import model.Endereco;
import java.sql.*;
import java.util.Scanner;

public class HospitalController {

    private final Scanner scanner = new Scanner(System.in);

    public Hospital buscarPorCodigoHospital(int codigo) {
        Hospital hospital = null;
        String query = "SELECT ID_HOSPITAL,RAZAO_SOCIAL,CNPJ,EMAIL,TELEFONE,CATEGORIA,ID_ENDERECO FROM HOSPITAL WHERE ID_HOSPITAL = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultado = statement.executeQuery();

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

                hospital = new Hospital(idHospital, razaoSocial, cnpj, email, telefone, categoria, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }

    public void cadastrarHospital() {
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
            String query = "INSERT INTO HOSPITAL (RAZAO_SOCIAL,CNPJ,EMAIL,TELEFONE,CATEGORIA,ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, razaoSocial);
                statement.setString(2, cnpj);
                statement.setString(3, email);
                statement.setString(4, telefone);
                statement.setString(5, categoria);
                statement.setInt(6, idEndereco);
                statement.executeUpdate();

                ResultSet registro = statement.getGeneratedKeys();
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

    public void atualizarHospital() {
        System.out.print("Digite o ID do hospital para atualizar: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine();

        Hospital hospitalExistente = buscarPorCodigoHospital(idHospital);

        if (hospitalExistente != null) {
            System.out.println("Deixe em branco para manter os dados atuais.");

            EnderecoController enderecoController = new EnderecoController();
            Endereco enderecoExistente = hospitalExistente.getIdEndereco();

            System.out.print("Logradouro (" + enderecoExistente.getLogradouro() + "): ");
            String logradouro = scanner.nextLine();
            if (logradouro.isEmpty())
                logradouro = enderecoExistente.getLogradouro();

            System.out.print("Numero (" + enderecoExistente.getNumero() + "): ");
            String numero = scanner.nextLine();
            if (numero.isEmpty())
                numero = enderecoExistente.getNumero();

            System.out.print("Bairro (" + enderecoExistente.getBairro() + "): ");
            String bairro = scanner.nextLine();
            if (bairro.isEmpty())
                bairro = enderecoExistente.getBairro();

            System.out.print("Cidade (" + enderecoExistente.getCidade() + "): ");
            String cidade = scanner.nextLine();
            if (cidade.isEmpty())
                cidade = enderecoExistente.getCidade();

            System.out.print("Estado (" + enderecoExistente.getEstado() + "): ");
            String estado = scanner.nextLine();
            if (estado.isEmpty())
                estado = enderecoExistente.getEstado();

            System.out.print("CEP (" + enderecoExistente.getCep() + "): ");
            String cep = scanner.nextLine();
            if (cep.isEmpty())
                cep = enderecoExistente.getCep();

            enderecoController.atualizarEndereco(enderecoExistente.getIdEndereco(), logradouro, numero, bairro, cidade,
                    estado,
                    cep);

            System.out.print("Nome do Hospital (" + hospitalExistente.getRazaoSocial() + "): ");
            String razaoSocial = scanner.nextLine();

            System.out.print("CNPJ (" + hospitalExistente.getCnpj() + "): ");
            String cnpj = scanner.nextLine();

            System.out.print("Email (" + hospitalExistente.getEmail() + "): ");
            String email = scanner.nextLine();

            System.out.print("Telefone (" + hospitalExistente.getTelefone() + "): ");
            String telefone = scanner.nextLine();

            System.out.print("Categoria (" + hospitalExistente.getCategoria() + "): ");
            String categoria = scanner.nextLine();

            String queryHospital = "UPDATE HOSPITAL SET RAZAO_SOCIAL = ?, CNPJ = ?, EMAIL = ?, TELEFONE = ?, CATEGORIA = ? WHERE ID_HOSPITAL = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(queryHospital)) {

                statement.setString(1, razaoSocial.isEmpty() ? hospitalExistente.getRazaoSocial() : razaoSocial);
                statement.setString(2, cnpj.isEmpty() ? hospitalExistente.getCnpj() : cnpj);
                statement.setString(3, email.isEmpty() ? hospitalExistente.getEmail() : email);
                statement.setString(4, telefone.isEmpty() ? hospitalExistente.getTelefone() : telefone);
                statement.setString(5, categoria.isEmpty() ? hospitalExistente.getCategoria() : categoria);
                statement.setInt(6, idHospital);
                int registro = statement.executeUpdate();

                if (registro > 0) {
                    System.out.println("Hospital atualizado com sucesso!");
                } else {
                    System.out.println("Hospital com codigo " + idHospital + " nao encontrado.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deletarHospital() {
        System.out.println("Deletar Hospital:");
        System.out.print("Digite o código do hospital a ser deletado: ");
        int idHospital = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tem certeza que deseja deletar este hospital? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        RemoverDepedencia depedencia = new RemoverDepedencia();

        boolean possuiDependenciaMedico = depedencia.verificarDependencia("HOSPITAL_MEDICO", "ID_HOSPITAL", idHospital);

        boolean possuiDependenciaHistorico = depedencia.verificarDependencia("HISTORICO", "ID_HOSPITAL", idHospital);

        if (possuiDependenciaMedico) {
            System.out.print("O hospital possui médicos associados. Deseja remover esses vínculos? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }
            depedencia.deletarDependencia("HOSPITAL_MEDICO", "ID_HOSPITAL", idHospital);
        }

        if (possuiDependenciaHistorico) {
            System.out.print("O hospital possui historico associados. Deseja remover esses vínculos? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("Sim")) {
                System.out.println("Operação cancelada.");
                return;
            }
            depedencia.deletarDependencia("HISTORICO", "ID_HOSPITAL", idHospital);
        }

        String queryHospital = "DELETE FROM HOSPITAL WHERE ID_HOSPITAL = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(queryHospital)) {

            statement.setInt(1, idHospital);
            int registrosAfetados = statement.executeUpdate();

            if (registrosAfetados > 0) {
                System.out.println("Hospital deletado com sucesso!");
            } else {
                System.out.println("Erro ao deletar o hospital. Hospital não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}