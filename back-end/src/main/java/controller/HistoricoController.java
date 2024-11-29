package controller;

import com.mongodb.client.MongoCollection;

import conexion.DatabaseConfig;
import model.Especialidade;
import model.Historico;
import model.Hospital;
import model.Medico;
import model.Paciente;
import Repository.EspecialidadeMedicoRepository;
import Repository.HistoricoRepository;
import Repository.HospitalRepository;
import Repository.MedicoRepository;
import Repository.PacienteRepository;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

public class HistoricoController {

    private final MongoCollection<Document> historicosCollection;
    private final HistoricoRepository historicoRepository;
    private final PacienteRepository pacienteRepository;
    private final HospitalRepository hospitalRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeMedicoRepository especialidadeMedicoRepository;
    private final Scanner scanner;

    public HistoricoController() {
        this.historicosCollection = DatabaseConfig.getDatabase().getCollection("historicos");
        this.historicoRepository = new HistoricoRepository();
        this.pacienteRepository = new PacienteRepository();
        this.hospitalRepository = new HospitalRepository();
        this.medicoRepository = new MedicoRepository();
        this.especialidadeMedicoRepository = new EspecialidadeMedicoRepository();
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarHistorico() {
        System.out.println("Cadastro de Histórico:");

        // Selecionar Paciente
        List<Paciente> pacientes = pacienteRepository.buscarTodosPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
            return;
        }
        System.out.println("\nSelecione o paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + " - " + pacientes.get(i).getNome());
        }
        int escolhaPaciente = obterEscolha(pacientes.size());
        Paciente pacienteSelecionado = pacientes.get(escolhaPaciente - 1);

        // Selecionar Hospital
        List<Hospital> hospitais = hospitalRepository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
            return;
        }
        System.out.println("\nSelecione o hospital:");
        for (int i = 0; i < hospitais.size(); i++) {
            System.out.println((i + 1) + " - " + hospitais.get(i).getRazaoSocial());
        }
        int escolhaHospital = obterEscolha(hospitais.size());
        Hospital hospitalSelecionado = hospitais.get(escolhaHospital - 1);

        // Selecionar Médico do Hospital
        List<Medico> medicos = medicoRepository.buscarMedicosPorHospital(hospitalSelecionado.getId());
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado para este hospital.");
            return;
        }
        System.out.println("\nSelecione o médico:");
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println((i + 1) + " - " + medicos.get(i).getNome());
        }
        int escolhaMedico = obterEscolha(medicos.size());
        Medico medicoSelecionado = medicos.get(escolhaMedico - 1);

        // Selecionar Especialidade do Médico
        List<Especialidade> especialidades = especialidadeMedicoRepository
                .buscarEspecialidadesPorMedico(medicoSelecionado.getId());
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada para este médico.");
            return;
        }
        System.out.println("\nSelecione a especialidade:");
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + " - " + especialidades.get(i).getNomeEspecialidade());
        }
        int escolhaEspecialidade = obterEscolha(especialidades.size());
        Especialidade especialidadeSelecionada = especialidades.get(escolhaEspecialidade - 1);

        // Coletar os demais dados
        System.out.print("Data da consulta (YYYY-MM-DD): ");
        String dataConsulta = scanner.nextLine();
        System.out.print("Observação: ");
        String observacao = scanner.nextLine();

        // Criar e salvar o histórico
        Historico historico = new Historico(null, dataConsulta, observacao, pacienteSelecionado, hospitalSelecionado,
                medicoSelecionado, especialidadeSelecionada);
        historicoRepository.inserirHistorico(historico);
        System.out.println("\nHistórico cadastrado com sucesso!");
    }

    private int obterEscolha(int limite) {
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + limite + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= limite) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
        return escolha;
    }

    public void atualizarHistorico() {
        // Listar todos os históricos disponíveis para atualização
        List<Historico> historicos = historicoRepository.buscarTodosHistoricos();
        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico encontrado para atualização.");
            return;
        }

        // Exibir os históricos disponíveis
        System.out.println("\nSelecione o histórico que deseja atualizar:");
        for (int i = 0; i < historicos.size(); i++) {
            Historico historico = historicos.get(i);
            System.out.println((i + 1) + " - Data: " + historico.getDataConsulta() +
                    ", Paciente: " + historico.getPaciente().getNome());
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + historicos.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= historicos.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar o histórico
        Historico historicoSelecionado = historicos.get(escolha - 1);

        // Exibir os dados atuais e permitir atualização
        System.out.println("\nAtualize os dados do histórico (ou deixe em branco para manter o atual):");

        System.out.print("Data da consulta [" + historicoSelecionado.getDataConsulta() + "]: ");
        String novaDataConsulta = scanner.nextLine();

        System.out.print("Observação [" + historicoSelecionado.getObservacao() + "]: ");
        String novaObservacao = scanner.nextLine();

        // Atualizar o hospital associado
        System.out.println("Hospital atual: " + historicoSelecionado.getHospital().getRazaoSocial());
        List<Hospital> hospitais = hospitalRepository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
            return;
        }
        System.out.println("Selecione o novo hospital:");
        for (int i = 0; i < hospitais.size(); i++) {
            Hospital hospital = hospitais.get(i);
            System.out.println((i + 1) + " - " + hospital.getRazaoSocial());
        }

        int escolhaHospital = obterEscolha(hospitais.size());
        Hospital hospitalAtualizado = hospitais.get(escolhaHospital - 1);

        // Atualizar o médico associado
        System.out.println("\nMédico atual: " + historicoSelecionado.getMedico().getNome());
        List<Medico> medicos = medicoRepository.buscarMedicosPorHospital(hospitalAtualizado.getId());
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado para este hospital.");
            return;
        }
        System.out.println("\nSelecione o novo médico:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println((i + 1) + " - " + medico.getNome());
        }

        int escolhaMedico = obterEscolha(medicos.size());
        Medico medicoAtualizado = medicos.get(escolhaMedico - 1);

        // Atualizar a especialidade associada
        System.out.println("Especialidade atual: " + historicoSelecionado.getEspecialidade().getNomeEspecialidade());
        List<Especialidade> especialidades = especialidadeMedicoRepository
                .buscarEspecialidadesPorMedico(medicoAtualizado.getId());
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada para este médico.");
            return;
        }
        System.out.println("\nSelecione a nova especialidade:");
        for (int i = 0; i < especialidades.size(); i++) {
            Especialidade especialidade = especialidades.get(i);
            System.out.println((i + 1) + " - " + especialidade.getNomeEspecialidade());
        }

        int escolhaEspecialidade = obterEscolha(especialidades.size());
        Especialidade especialidadeAtualizada = especialidades.get(escolhaEspecialidade - 1);

        // Criar o histórico atualizado
        Historico historicoAtualizado = new Historico(
                historicoSelecionado.getId(),
                novaDataConsulta.isEmpty() ? historicoSelecionado.getDataConsulta() : novaDataConsulta,
                novaObservacao.isEmpty() ? historicoSelecionado.getObservacao() : novaObservacao,
                historicoSelecionado.getPaciente(), // Mantém o paciente
                hospitalAtualizado,
                medicoAtualizado,
                especialidadeAtualizada);

        // Atualizar no repositório
        historicoRepository.atualizarHistorico(historicoAtualizado);
        System.out.println("\nHistórico atualizado com sucesso!");
    }

    public void deletarHistorico() {
        // Buscar todos os históricos
        List<Historico> historicos = historicoRepository.buscarTodosHistoricos();
        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico encontrado para exclusão.");
            return;
        }

        // Exibir a lista de históricos disponíveis
        System.out.println("\nSelecione o histórico que deseja excluir:");
        for (int i = 0; i < historicos.size(); i++) {
            Historico historico = historicos.get(i);
            System.out.println((i + 1) + " - Paciente: " + historico.getPaciente().getNome() +
                    " | Data da Consulta: " + historico.getDataConsulta() +
                    " | Observação: " + historico.getObservacao());
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + historicos.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= historicos.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar o histórico e confirmar a exclusão
        Historico historicoSelecionado = historicos.get(escolha - 1);

        System.out.println("Você selecionou o histórico:");
        System.out.println("Paciente: " + historicoSelecionado.getPaciente().getNome());
        System.out.println("Data da Consulta: " + historicoSelecionado.getDataConsulta());
        System.out.println("Observação: " + historicoSelecionado.getObservacao());

        System.out.print("\nDeseja realmente excluir este histórico? (Sim/Não): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        if (confirmacao.equals("sim")) {
            // Excluir o histórico do repositório
            historicoRepository.excluirHistorico(historicoSelecionado.getId());
            System.out.println("Histórico excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

}
