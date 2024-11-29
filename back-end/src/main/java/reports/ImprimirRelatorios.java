package reports;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.bson.Document;
import utils.Config;

public class ImprimirRelatorios {
    private final Scanner scanner = new Scanner(System.in);

    Relatorios relatorios = new Relatorios();

    @SuppressWarnings("unchecked")
    private List<Document> getList(Document document, String key) {
        Object value = document.get(key);
        if (value instanceof List<?>) {
            return (List<Document>) value;
        }
        return List.of(); // Retorna lista vazia se não for do tipo esperado
    }

    public void imprimirTodosRelatorios() {
        System.out.println("=== Início de Todos os Relatórios ===\n");

        System.out.println("\n");
        imprimirRelatorioMedicos(false);
        System.out.println("\n");

        imprimirRelatorioEspecialidadeMedicos(false);
        System.out.println("\n");

        imprimirRelatorioHospitalMedicos(false);
        System.out.println("\n");

        imprimirRelatorioAgrupamentoEsp(false);
        System.out.println("\n");

        imprimirRelatorioEspecialidades(false);
        System.out.println("\n");

        imprimirRelatorioHospitais(false);
        System.out.println("\n");

        imprimirRelatorioPacientes(false);
        System.out.println("\n");

        imprimirRelatorioHistorico(false);
        System.out.println("\n");

        System.out.println("\n=== Fim de Todos os Relatórios ===");

        System.out.println("Aperte Enter para retornar...");
        scanner.nextLine();
        Config.limparConsole(1);
    }

    public void imprimirRelatorioMedicos(boolean esperarUsuario) {
        List<Document> medicos = relatorios.getRelatorioMedicos();
        String linha = "+--------------------------+----------------------+-----------------+";
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-20s | %-15s |%n";
            String formatarLinha = "| %-24s | %-20s | %-15s |%n";

            System.out.println("Relatório de Médicos:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID (MongoDB)", "Nome", "Conselho");
            System.out.println(linha);

            for (Document medico : medicos) {
                try {
                    // Verifique se os campos existem no documento
                    String id = medico.getObjectId("_id").toString();
                    String nome = medico.getString("nome");
                    String conselho = medico.getString("conselho");

                    System.out.format(formatarLinha,
                            id != null ? id : "N/A",
                            nome != null ? nome : "N/A",
                            conselho != null ? conselho : "N/A");
                } catch (Exception e) {
                    System.err.println("Erro ao processar documento: " + medico);
                    e.printStackTrace();
                }
            }

            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioEspecialidadeMedicos(boolean esperarUsuario) {
        List<Document> especialidadeMedicos = relatorios.getRelatorioEspecialidadeMedicos();
        String linha = "+--------------------------+--------------------------+";
        if (especialidadeMedicos.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-24s |%n";
            String formatarLinha = "| %-24s | %-24s |%n";

            System.out.println("Relatório de Médicos por Especialidade:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome do Médico", "Nome da Especialidade");
            System.out.println(linha);

            for (Document registro : especialidadeMedicos) {
                String medicoNome = registro.getString("medicoNome");
                String especialidadeNome = registro.getString("especialidadeNome");

                System.out.format(formatarLinha,
                        medicoNome != null ? medicoNome : "N/A",
                        especialidadeNome != null ? especialidadeNome : "N/A");
            }

            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioHospitalMedicos(boolean esperarUsuario) {
        List<Document> hospitalMedicos = relatorios.getRelatorioHospitaisMedicos();
        String linha = "+--------------------------+--------------------------+--------------------------+";
        if (hospitalMedicos.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-24s | %-24s |%n";
            String formatarLinha = "| %-24s | %-24s | %-24s |%n";

            System.out.println("Relatório de Médicos por Hospital:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome do Médico", "Nome do Hospital", "Categoria");
            System.out.println(linha);

            for (Document registro : hospitalMedicos) {
                // Acessa diretamente os campos do documento principal
                String medicoNome = registro.getString("medicoNome");
                String hospitalNome = registro.getString("hospitalNome");
                String categoria = registro.getString("hospitalCategoria");

                System.out.format(formatarLinha,
                        medicoNome != null ? medicoNome : "N/A",
                        hospitalNome != null ? hospitalNome : "N/A",
                        categoria != null ? categoria : "N/A");
            }

            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioAgrupamentoEsp(boolean esperarUsuario) {
        List<Document> agrupamentoEspecialidades = relatorios.getRelatorioAgrupamentoEsp();
        String linha = "+--------------------------+--------------------------+";
        if (agrupamentoEspecialidades.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-24s |%n";
            String formatarLinha = "| %-24s | %-24d |%n";

            System.out.println("Relatório de Quantidade de Médicos por Especialidade:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Especialidade", "Quantidade");
            System.out.println(linha);

            for (Document registro : agrupamentoEspecialidades) {
                String especialidadeNome = registro.getString("nomeEspecialidade");
                Integer quantidadeMedicos = registro.getInteger("quantidadeMedicos", 0);

                System.out.format(formatarLinha,
                        especialidadeNome != null ? especialidadeNome : "N/A",
                        quantidadeMedicos);
            }
            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioEspecialidades(boolean esperarUsuario) {
        List<Document> especialidades = relatorios.getRelatorioEspecialidades();
        String linha = "+--------------------------+--------------------------+";
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada.");
        } else {
            String formatarCabecalho = "| %-24s | %-24s |%n";
            String formatarLinha = "| %-24s | %-24s |%n";

            System.out.println("Relatório de Especialidades:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome");
            System.out.println(linha);

            for (Document especialidade : especialidades) {
                String id = especialidade.getObjectId("_id").toString();
                String nomeEspecialidade = especialidade.getString("nomeEspecialidade");

                System.out.format(formatarLinha,
                        id,
                        nomeEspecialidade != null ? nomeEspecialidade : "N/A");
            }
            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioHospitais(boolean esperarUsuario) {
        List<Document> hospitais = relatorios.getRelatorioHospitais();
        String linha = "+--------------------------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-24s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatório de Hospitais:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Razão Social", "CNPJ", "Categoria", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);

            for (Document hospital : hospitais) {
                String id = hospital.getObjectId("_id").toString();
                String razaoSocial = hospital.getString("razaoSocial");
                String cnpj = hospital.getString("cnpj");
                String categoria = hospital.getString("categoria");
                String telefone = hospital.getString("telefone");
                String email = hospital.getString("email");

                // Recupera enderecoInfo como um único Document
                Document endereco = hospital.get("enderecoInfo", Document.class);

                System.out.format(formatarLinha,
                        id,
                        razaoSocial != null ? razaoSocial : "N/A",
                        cnpj != null ? cnpj : "N/A",
                        categoria != null ? categoria : "N/A",
                        telefone != null ? telefone : "N/A",
                        email != null ? email : "N/A",
                        endereco != null ? endereco.getString("logradouro") : "N/A",
                        endereco != null ? endereco.getString("bairro") : "N/A",
                        endereco != null ? endereco.getString("cidade") : "N/A");
            }
            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioPacientes(boolean esperarUsuario) {
        List<Document> pacientes = relatorios.getRelatorioPacientes();
        String linha = "+--------------------------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-24s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatório de Pacientes:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome", "Dt Nascimento", "CPF", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);

            for (Document paciente : pacientes) {
                String id = paciente.getObjectId("_id").toString();
                String nome = paciente.getString("nome");
                String dataNascimento = paciente.getString("dataNascimento");
                String cpf = paciente.getString("cpf");
                String telefone = paciente.getString("telefone");
                String email = paciente.getString("email");

                // Recupera enderecoInfo como um único Document
                Document endereco = paciente.get("enderecoInfo", Document.class);

                System.out.format(formatarLinha,
                        id,
                        nome != null ? nome : "N/A",
                        dataNascimento != null ? dataNascimento : "N/A",
                        cpf != null ? cpf : "N/A",
                        telefone != null ? telefone : "N/A",
                        email != null ? email : "N/A",
                        endereco != null ? endereco.getString("logradouro") : "N/A",
                        endereco != null ? endereco.getString("bairro") : "N/A",
                        endereco != null ? endereco.getString("cidade") : "N/A");
            }
            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

    public void imprimirRelatorioHistorico(boolean esperarUsuario) {
        List<Document> historicos = relatorios.getRelatorioHistoricos();
        String linha = "+--------------------------+--------------+---------------------------+---------------------------+----------------------+-------------------------------------+-----------------+--------------------------------------------------------------+";
        if (historicos.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            String formatarCabecalho = "| %-24s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";
            String formatarLinha = "| %-24s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";

            System.out.println("Relatório de Históricos:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Dt Consulta", "Paciente", "Médico", "Especialidade",
                    "Hospital", "Categoria", "Observação");
            System.out.println(linha);

            for (Document historico : historicos) {
                String id = historico.getObjectId("_id").toString();
                String dataConsulta = historico.getString("dataConsulta");

                // Recupera as informações relacionadas como um único Document
                Document paciente = historico.get("pacienteInfo", Document.class);
                Document medico = historico.get("medicoInfo", Document.class);
                Document especialidade = historico.get("especialidadeInfo", Document.class);
                Document hospital = historico.get("hospitalInfo", Document.class);

                System.out.format(formatarLinha,
                        id,
                        dataConsulta != null ? dataConsulta : "N/A",
                        paciente != null ? paciente.getString("nome") : "N/A",
                        medico != null ? medico.getString("nome") : "N/A",
                        especialidade != null ? especialidade.getString("nomeEspecialidade") : "N/A",
                        hospital != null ? hospital.getString("razaoSocial") : "N/A",
                        hospital != null ? hospital.getString("categoria") : "N/A",
                        historico.getString("observacao") != null ? historico.getString("observacao") : "N/A");
            }
            System.out.println(linha);
            if (esperarUsuario) {
                System.out.println("Aperte Enter para continuar...");
                scanner.nextLine();
                Config.limparConsole(1);
            }
        }
    }

}
