package reports;

import java.util.List;
import java.util.Scanner;
import model.Especialidade;
import model.Historico;
import model.Hospital;
import model.Medico;
import model.Paciente;
import utils.Config;

public class ImprimirRelatorios {
    private final Scanner scanner = new Scanner(System.in);

    Relatorios relatorios = new Relatorios();

    public void imprimirRelatorioMedicos() {
        List<Medico> medicos = relatorios.getRelatorioMedicos();
        String linha = "+-------+----------------------+-----------------+";
        if (medicos.isEmpty()) {
            System.out.println("Nenhum medico encontrado.");
        } else {

            String formatarCabecalho = "| %-5s | %-20s | %-15s |%n";
            String formatarLinha = "| %-5d | %-20s | %-15s |%n";

            System.out.println("Relatorio de Medicos:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome", "Conselho");
            System.out.println(linha);

            for (Medico medico : medicos) {
                System.out.format(formatarLinha, medico.getIdMedico(), medico.getNomeMedico(), medico.getConselho());
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);

        }
    }

    public void imprimirRelatorioEspecialidades() {
        List<Especialidade> especialidades = relatorios.getRelatorioEspecialidade();
        String linha = "+-------+----------------------+";
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada.");
        } else {
            String formatarCabecalho = "| %-5s | %-20s |%n";
            String formatarLinha = "| %-5d | %-20s |%n";

            System.out.println("Relatorio de Especialidades:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome");
            System.out.println(linha);

            for (Especialidade especialidade : especialidades) {
                System.out.format(formatarLinha, especialidade.getIdEspecialidade(),
                        especialidade.getNomeEspecialidade());
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);

        }
    }

    public void imprimirRelatorioHospitais() {
        List<Hospital> hospitais = relatorios.getRelatorioHospitais();
        String linha = "+-------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatorio de Hospitais:");
            System.out.println(linha);

            System.out.format(formatarCabecalho, "ID", "Razao Social", "CNPJ", "Categoria", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);
            for (Hospital hospital : hospitais) {
                System.out.format(formatarLinha,
                        hospital.getIdHospital(),
                        hospital.getRazaoSocial(),
                        hospital.getCnpj(),
                        hospital.getCategoria(),
                        hospital.getTelefone(),
                        hospital.getEmail(),
                        hospital.getIdEndereco().getLogradouro(),
                        hospital.getIdEndereco().getBairro(),
                        hospital.getIdEndereco().getCidade());
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioPacientes() {
        List<Paciente> pacientes = relatorios.getRelatorioPacientes();
        String linha = "+-------+--------------------------------+-----------------+-----------------+--------------+--------------------------------+----------------------+-------------------------+---------------+";
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            String formatarCabecalho = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";
            String formatarLinha = "| %-5s | %-30s | %-15s | %-15s | %-12s | %-30s | %-20s | %-23s | %-13s |%n";

            System.out.println("Relatorio de Hospitais:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "ID", "Nome Paciente", "Dt Nascimento", "CPF", "Telefone", "Email",
                    "Logradouro", "Bairro", "Cidade");
            System.out.println(linha);
            for (Paciente paciente : pacientes) {
                System.out.format(formatarLinha,
                        paciente.getIdPaciente(),
                        paciente.getNomePaciente(),
                        paciente.getDataNascimento(),
                        paciente.getCpf(),
                        paciente.getTelefone(),
                        paciente.getEmail(),
                        paciente.getIdEndereco().getLogradouro(),
                        paciente.getIdEndereco().getBairro(),
                        paciente.getIdEndereco().getCidade());
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioHistorico() {
        List<Historico> historicos = relatorios.getRelatorioHistoricos();
        String linha = "+-------+--------------+---------------------------+---------------------------+----------------------+-------------------------------------+-----------------+--------------------------------------------------------------+";
        if (historicos.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {

            String formatarCabecalho = "| %-5s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";
            String formatarLinha = "| %-5s | %-12s | %-25s | %-25s | %-20s | %-35s | %-15s | %-60s |%n";

            System.out.println("Relatorio de Hospitais:");

            System.out.println(linha);

            System.out.format(formatarCabecalho, "ID", "Dt Consulta", "Nome Paciente", "Nome do Medico",
                    "Especialidade",
                    "Hospital", "Categoria", "Observação");

            System.out.println(linha);

            for (Historico historico : historicos) {
                System.out.format(formatarLinha,
                        historico.getIdHistorico(),
                        historico.getDataConsulta(),
                        historico.getIdPaciente().getNomePaciente(),
                        historico.getIdMedico().getNomeMedico(),
                        historico.getIdEspecialidade().getNomeEspecialidade(),
                        historico.getIdHospital().getRazaoSocial(),
                        historico.getIdHospital().getCategoria(),
                        historico.getObservacao());
            }

            System.out.println(linha);

            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioEspecialidadeMedicos() {
        List<String[]> relatorio = relatorios.getRelatorioEspecialidadeMedicos();
        String linha = "+--------------------------------+--------------------------------+";
        if (relatorio.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-30s | %-30s |%n";
            String formatarLinha = "| %-30s | %-30s |%n";

            System.out.println("Relatorio de Medicos e Especialidades:");
            System.out.println(linha);

            System.out.format(formatarCabecalho, "Nome do Medico", "Nome da Especialidade");

            System.out.println(linha);

            for (String[] registro : relatorio) {
                System.out.format(formatarLinha, registro[0], registro[1]);
            }
            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioHospitalMedicos() {
        List<String[]> relatorio = relatorios.getRelatorioHospitalMedicos();
        String linha = "+--------------------------------+-----------------+--------------------------------+----------------------+--------------------------------+";
        if (relatorio.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-30s | %-15s | %-30s | %-20s | %-30s |%n";
            String formatarLinha = "| %-30s | %-15s | %-30s | %-20s | %-30s |%n";

            System.out.println("Relatorio de Medicos, Hospitais e Especialidades:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome do Medico", "Conselho", "Hospital", "Categoria",
                    "Especialidade");
            System.out.println(linha);

            for (String[] registro : relatorio) {
                System.out.format(formatarLinha, registro[0], registro[1], registro[2], registro[3], registro[4]);
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

    public void imprimirRelatorioAgrupamentoEsp() {
        List<String[]> relatorio = relatorios.getRelatorioAgrupamentoEsp();
        String linha = "+--------------------------------+----------------------+";
        if (relatorio.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
        } else {
            String formatarCabecalho = "| %-30s | %-20s |%n";
            String formatarLinha = "| %-30s | %-20s |%n";

            System.out.println("Quantitativo de Medicos por Especialidade:");
            System.out.println(linha);
            System.out.format(formatarCabecalho, "Nome Especialidade", "Qts Med Cadastrado");
            System.out.println(linha);

            for (String[] registro : relatorio) {
                System.out.format(formatarLinha, registro[0], registro[1]);
            }

            System.out.println(linha);
            System.out.println("Aperte enter para sair");

            scanner.nextLine();
            Config.limparConsole(1);
        }
    }

}
