package controller;

import conexion.DatabaseConfig;
import model.Paciente;
import model.Endereco;
import java.sql.*;
import java.util.Scanner;

public class PacienteController {

    private final Scanner scanner = new Scanner(System.in);

    public Paciente buscarPorCodigoPaciente(int codigo) {
        Paciente paciente = null;
        String query = "SELECT ID_PACIENTE, NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO FROM PACIENTE WHERE ID_PACIENTE = ?";

        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setInt(1, codigo);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idPaciente = resultado.getInt("ID_PACIENTE");
                String nomePaciente = resultado.getString("NOME");
                String email = resultado.getString("EMAIL");
                String telefone = resultado.getString("TELEFONE");
                String dataNascimento = resultado.getString("DATA_NASCIMENTO");
                String cpf = resultado.getString("CPF");
                int idEndereco = resultado.getInt("ID_ENDERECO");

                EnderecoController enderecoController = new EnderecoController();
                Endereco endereco = enderecoController.buscarEnderecoPorCodigo(idEndereco);

                paciente = new Paciente(idPaciente, nomePaciente, dataNascimento, email, telefone, cpf, endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    public void cadastrarPaciente() {
        EnderecoController enderecoController = new EnderecoController();

        System.out.println("Cadastro de Paciente:");
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Nome: ");
        String nomePaciente = scanner.nextLine();
        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
        String dataNascimento = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        int idEndereco = enderecoController.cadastrarEndereco(logradouro, numero, bairro, cidade, estado, cep);

        if (idEndereco != -1) {
            String queryPaciente = "INSERT INTO PACIENTE (NOME, EMAIL, TELEFONE, DATA_NASCIMENTO, CPF, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(queryPaciente,
                            Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nomePaciente);
                statement.setString(2, email);
                statement.setString(3, telefone);
                statement.setString(4, dataNascimento);
                statement.setString(5, cpf);
                statement.setInt(6, idEndereco);
                statement.executeUpdate();

                ResultSet registro = statement.getGeneratedKeys();
                if (registro.next()) {
                    int idPaciente = registro.getInt(1);
                    System.out.println("Paciente cadastrado com sucesso! ID: " + idPaciente);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao cadastrar o endereço. Paciente não cadastrado.");
        }
    }

    public void atualizarPaciente() {
        System.out.print("Digite o ID do paciente para atualizar: ");
        int idPaciente = Integer.parseInt(scanner.nextLine());

        Paciente pacienteExistente = buscarPorCodigoPaciente(idPaciente);

        if (pacienteExistente != null) {
            EnderecoController enderecoController = new EnderecoController();
            System.out.println("Deixe em branco para manter os dados atuais.");

            System.out.print("Logradouro (" + pacienteExistente.getIdEndereco().getLogradouro() + "): ");
            String logradouro = scanner.nextLine();
            System.out.print("Número (" + pacienteExistente.getIdEndereco().getNumero() + "): ");
            String numero = scanner.nextLine();
            System.out.print("Bairro (" + pacienteExistente.getIdEndereco().getBairro() + "): ");
            String bairro = scanner.nextLine();
            System.out.print("Cidade (" + pacienteExistente.getIdEndereco().getCidade() + "): ");
            String cidade = scanner.nextLine();
            System.out.print("Estado (" + pacienteExistente.getIdEndereco().getEstado() + "): ");
            String estado = scanner.nextLine();
            System.out.print("CEP (" + pacienteExistente.getIdEndereco().getCep() + "): ");
            String cep = scanner.nextLine();
            System.out.print("Nome (" + pacienteExistente.getNomePaciente() + "): ");
            String nome = scanner.nextLine();
            System.out.print("Data de Nascimento (" + pacienteExistente.getDataNascimento() + "): ");
            String dataNascimento = scanner.nextLine();
            System.out.print("CPF (" + pacienteExistente.getCpf() + "): ");
            String cpf = scanner.nextLine();
            System.out.print("Email (" + pacienteExistente.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Telefone (" + pacienteExistente.getTelefone() + "): ");
            String telefone = scanner.nextLine();

            enderecoController.atualizarEndereco(
                    pacienteExistente.getIdEndereco().getIdEndereco(),
                    logradouro.isEmpty() ? pacienteExistente.getIdEndereco().getLogradouro() : logradouro,
                    numero.isEmpty() ? pacienteExistente.getIdEndereco().getNumero() : numero,
                    bairro.isEmpty() ? pacienteExistente.getIdEndereco().getBairro() : bairro,
                    cidade.isEmpty() ? pacienteExistente.getIdEndereco().getCidade() : cidade,
                    estado.isEmpty() ? pacienteExistente.getIdEndereco().getEstado() : estado,
                    cep.isEmpty() ? pacienteExistente.getIdEndereco().getCep() : cep);

            String queryPaciente = "UPDATE PACIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, DATA_NASCIMENTO = ?, CPF = ? WHERE ID_PACIENTE = ?";

            try (Connection conexao = DatabaseConfig.getConnection();
                    PreparedStatement statement = conexao.prepareStatement(queryPaciente)) {

                statement.setString(1, nome.isEmpty() ? pacienteExistente.getNomePaciente() : nome);
                statement.setString(2, email.isEmpty() ? pacienteExistente.getEmail() : email);
                statement.setString(3, telefone.isEmpty() ? pacienteExistente.getTelefone() : telefone);
                statement.setString(4,
                        dataNascimento.isEmpty() ? pacienteExistente.getDataNascimento() : dataNascimento);
                statement.setString(5, cpf.isEmpty() ? pacienteExistente.getCpf() : cpf);
                statement.setInt(6, idPaciente);
                statement.executeUpdate();

                System.out.println("Paciente atualizado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    public void deletarPaciente() {
        System.out.print("Digite o ID do paciente para deletar: ");
        int idPaciente = Integer.parseInt(scanner.nextLine());

        System.out.print("Tem certeza que deseja deletar este paciente? (Sim/Não): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("Sim")) {
            System.out.println("Operação cancelada.");
            return;
        }

        RemoverDepedencia depedencia = new RemoverDepedencia();

        boolean possuiDependencia = depedencia.verificarDependencia("HISTORICO", "ID_PACIENTE", idPaciente);
        if (possuiDependencia) {
            System.out.print("O paciente possui histórico associado. Deseja remover esses registros? (Sim/Não): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("Sim")) {
                depedencia.deletarDependencia("HISTORICO", "ID_PACIENTE", idPaciente);
            } else {
                System.out.println("Operação cancelada.");
                return;
            }
        }

        String queryPaciente = "DELETE FROM PACIENTE WHERE ID_PACIENTE = ?";
        try (Connection conexao = DatabaseConfig.getConnection();
                PreparedStatement statement = conexao.prepareStatement(queryPaciente)) {

            statement.setInt(1, idPaciente);
            int registrosAfetados = statement.executeUpdate();

            if (registrosAfetados > 0) {
                System.out.println("Paciente deletado com sucesso!");
            } else {
                System.out.println("Paciente não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}