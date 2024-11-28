package controller;

import com.mongodb.client.MongoCollection;

import Repository.EspecialidadeMedicoRepository;
import Repository.HistoricoRepository;
import Repository.HospitalRepository;
import Repository.MedicoRepository;
import Repository.PacienteRepository;
import conexion.DatabaseConfig;
import model.Especialidade;
import model.Historico;
import model.Hospital;
import model.Medico;
import model.Paciente;

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
        System.out.println("Selecione o paciente:");
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
        System.out.println("Selecione o hospital:");
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
        System.out.println("Selecione o médico:");
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
        System.out.println("Selecione a especialidade:");
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
        System.out.println("Histórico cadastrado com sucesso!");
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

        System.out.print("Digite o ID do histórico que deseja atualizar: ");
        String idHistorico = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idHistorico));
        Document historicoAtual = historicosCollection.find(filtro).first();

        if (historicoAtual == null) {
            System.out.println("Histórico não encontrado.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Nova data da consulta (YYYY-MM-DD): ");
        String novaDataConsulta = scanner.nextLine();
        System.out.print("Nova observação: ");
        String novaObservacao = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novaDataConsulta.isBlank()) {
            atualizacao.append("dataConsulta", novaDataConsulta);
        }
        if (!novaObservacao.isBlank()) {
            atualizacao.append("observacao", novaObservacao);
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        historicosCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Histórico atualizado com sucesso.");
    }

    public void deletarHistorico() {

        System.out.print("Digite o ID do histórico que deseja excluir: ");
        String idHistorico = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idHistorico));
        Document historicoAtual = historicosCollection.find(filtro).first();

        if (historicoAtual == null) {
            System.out.println("Histórico não encontrado.");
            return;
        }

        historicosCollection.deleteOne(filtro);
        System.out.println("Histórico excluído com sucesso.");
    }
}
