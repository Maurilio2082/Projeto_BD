package reports;

import java.util.List;
import java.util.Scanner;
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

    public void imprimirRelatorioMedicos() {
        List<Document> medicos = relatorios.getRelatorioMedicos();
        String linha = "+-------+----------------------+-----------------+";
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-20s | %-15s |%n";
            String formatarLinha = "| %-5s | %-20s | %-15s |%n";

            System.out.println("Relatório de Médicos:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome", "Conselho");
            System.out.println(linha);

            for (Document medico : medicos) {
                System.out.format(formatarLinha,
                        medico.getObjectId("_id"),
                        medico.getString("nome"),
                        medico.getString("conselho"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioEspecialidadeMedicos() {
        List<Document> especialidadeMedicos = relatorios.getRelatorioEspecialidadeMedicos();
        String linha = "+----------------------+----------------------+";
        if (especialidadeMedicos.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-20s | %-20s |%n";
            String formatarLinha = "| %-20s | %-20s |%n";

            System.out.println("Relatório de Médicos por Especialidade:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome do Médico", "Nome da Especialidade");
            System.out.println(linha);

            for (Document registro : especialidadeMedicos) {
                System.out.format(formatarLinha,
                        registro.getString("medicoNome"),
                        registro.getString("especialidadeNome"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioHospitalMedicos() {
        List<Document> hospitalMedicos = relatorios.getRelatorioHospitaisMedicos();

        String linha = "+----------------------+----------------------+----------------------+";
        if (hospitalMedicos.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-20s | %-20s | %-20s |%n";
            String formatarLinha = "| %-20s | %-20s | %-20s |%n";

            System.out.println("Relatório de Médicos por Hospital:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome do Médico", "Nome do Hospital", "Categoria");
            System.out.println(linha);

            for (Document registro : hospitalMedicos) {
                System.out.format(formatarLinha,
                        registro.getString("medicoNome"),
                        registro.getString("hospitalNome"),
                        registro.getString("categoria"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioAgrupamentoEsp() {
        List<Document> agrupamentoEspecialidades = relatorios.getRelatorioAgrupamentoEsp();
        String linha = "+----------------------+----------------------+";
        if (agrupamentoEspecialidades.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-20s | %-20s |%n";
            String formatarLinha = "| %-20s | %-20s |%n";

            System.out.println("Relatório de Quantidade de Médicos por Especialidade:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Especialidade", "Quantidade");
            System.out.println(linha);

            for (Document registro : agrupamentoEspecialidades) {
                System.out.format(formatarLinha,
                        registro.getString("especialidadeNome"),
                        registro.getInteger("quantidadeMedicos"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioEspecialidades() {
        List<Document> especialidades = relatorios.getRelatorioEspecialidades();
        String linha = "+-------+----------------------+";
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada.");
        } else {
            String formatarCabecalho = "| %-5s | %-20s |%n";
            String formatarLinha = "| %-5s | %-20s |%n";

            System.out.println("Relatório de Especialidades:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome");
            System.out.println(linha);

            for (Document especialidade : especialidades) {
                System.out.format(formatarLinha,
                        especialidade.getObjectId("_id"),
                        especialidade.getString("nomeEspecialidade"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioHospitais() {
        List<Document> hospitais = relatorios.getRelatorioHospitais();
        String linha = "+-------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatório de Hospitais:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Razão Social", "CNPJ", "Categoria", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);

            for (Document hospital : hospitais) {
                List<Document> enderecoList = getList(hospital, "enderecoInfo");
                Document endereco = enderecoList.isEmpty() ? null : enderecoList.get(0);

                System.out.format(formatarLinha,
                        hospital.getObjectId("_id"),
                        hospital.getString("razaoSocial"),
                        hospital.getString("cnpj"),
                        hospital.getString("categoria"),
                        hospital.getString("telefone"),
                        hospital.getString("email"),
                        endereco != null ? endereco.getString("logradouro") : "N/A",
                        endereco != null ? endereco.getString("bairro") : "N/A",
                        endereco != null ? endereco.getString("cidade") : "N/A");
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioPacientes() {
        List<Document> pacientes = relatorios.getRelatorioPacientes();
        String linha = "+-------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatório de Pacientes:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome", "Dt Nascimento", "CPF", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);

            for (Document paciente : pacientes) {
                List<Document> enderecoList = getList(paciente, "enderecoInfo");
                Document endereco = enderecoList.isEmpty() ? null : enderecoList.get(0);

                System.out.format(formatarLinha,
                        paciente.getObjectId("_id"),
                        paciente.getString("nome"),
                        paciente.getString("dataNascimento"),
                        paciente.getString("cpf"),
                        paciente.getString("telefone"),
                        paciente.getString("email"),
                        endereco != null ? endereco.getString("logradouro") : "N/A",
                        endereco != null ? endereco.getString("bairro") : "N/A",
                        endereco != null ? endereco.getString("cidade") : "N/A");
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioHistorico() {
        List<Document> historicos = relatorios.getRelatorioHistoricos();
        String linha = "+-------+--------------+---------------------------+---------------------------+----------------------+-------------------------------------+-----------------+--------------------------------------------------------------+";
        if (historicos.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";
            String formatarLinha = "| %-5s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";

            System.out.println("Relatório de Históricos:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Dt Consulta", "Paciente", "Médico", "Especialidade",
                    "Hospital", "Categoria", "Observação");
            System.out.println(linha);

            for (Document historico : historicos) {
                List<Document> pacienteInfo = getList(historico, "pacienteInfo");
                List<Document> medicoInfo = getList(historico, "medicoInfo");
                List<Document> especialidadeInfo = getList(historico, "especialidadeInfo");
                List<Document> hospitalInfo = getList(historico, "hospitalInfo");

                System.out.format(formatarLinha,
                        historico.getObjectId("_id"),
                        historico.getString("dataConsulta"),
                        pacienteInfo.isEmpty() ? "N/A" : pacienteInfo.get(0).getString("nome"),
                        medicoInfo.isEmpty() ? "N/A" : medicoInfo.get(0).getString("nome"),
                        especialidadeInfo.isEmpty() ? "N/A" : especialidadeInfo.get(0).getString("nomeEspecialidade"),
                        hospitalInfo.isEmpty() ? "N/A" : hospitalInfo.get(0).getString("razaoSocial"),
                        hospitalInfo.isEmpty() ? "N/A" : hospitalInfo.get(0).getString("categoria"),
                        historico.getString("observacao"));
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");
            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

}
