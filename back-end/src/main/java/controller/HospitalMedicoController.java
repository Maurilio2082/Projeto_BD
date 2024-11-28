package controller;

import com.mongodb.client.MongoCollection;

import Repository.HospitalMedicoRepository;
import Repository.HospitalRepository;
import Repository.MedicoRepository;
import conexion.DatabaseConfig;
import model.Hospital;
import model.HospitalMedico;
import model.Medico;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

public class HospitalMedicoController {

    private final MongoCollection<Document> hospitalMedicoCollection;
    private final HospitalMedicoRepository hospitalMedicoRepository;
    private final HospitalRepository hospitalRepository;
    private final MedicoRepository medicoRepository;
    private final Scanner scanner;

    public HospitalMedicoController() {
        this.hospitalMedicoCollection = DatabaseConfig.getDatabase().getCollection("hospitais_medico");
        this.hospitalMedicoRepository = new HospitalMedicoRepository();
        this.hospitalRepository = new HospitalRepository();
        this.medicoRepository = new MedicoRepository();
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarMedicoXHospital() {
        // Listar médicos disponíveis
        List<Medico> medicos = medicoRepository.buscarTodosMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
            return;
        }

        System.out.println("Selecione o médico:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println((i + 1) + " - " + medico.getNome() + " (" + medico.getConselho() + ")");
        }

        int escolhaMedico;
        while (true) {
            System.out.print("Escolha o número do médico: ");
            try {
                escolhaMedico = Integer.parseInt(scanner.nextLine());
                if (escolhaMedico >= 1 && escolhaMedico <= medicos.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        Medico medicoEscolhido = medicos.get(escolhaMedico - 1);

        // Listar hospitais disponíveis
        List<Hospital> hospitais = hospitalRepository.buscarTodosHospitais();
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
            return;
        }

        System.out.println("Selecione o hospital:");
        for (int i = 0; i < hospitais.size(); i++) {
            Hospital hospital = hospitais.get(i);
            System.out.println((i + 1) + " - " + hospital.getRazaoSocial() + " (" + hospital.getCategoria() + ")");
        }

        int escolhaHospital;
        while (true) {
            System.out.print("Escolha o número do hospital: ");
            try {
                escolhaHospital = Integer.parseInt(scanner.nextLine());
                if (escolhaHospital >= 1 && escolhaHospital <= hospitais.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        Hospital hospitalEscolhido = hospitais.get(escolhaHospital - 1);

        // Relacionar médico e hospital
        HospitalMedico relacao = new HospitalMedico(null, hospitalEscolhido, medicoEscolhido);
        hospitalMedicoRepository.inserirRelacao(relacao);
        System.out.println("Relacionamento entre médico e hospital cadastrado com sucesso!");
    }

    public void atualizarMedicoXHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação médico-hospital que deseja atualizar: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        Document relacaoAtual = hospitalMedicoCollection.find(filtro).first();

        if (relacaoAtual == null) {
            System.out.println("Relação não encontrada.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Novo ID do hospital: ");
        String novoHospitalId = scanner.nextLine();
        System.out.print("Novo ID do médico: ");
        String novoMedicoId = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novoHospitalId.isBlank()) {
            atualizacao.append("hospitalId", new ObjectId(novoHospitalId));
        }
        if (!novoMedicoId.isBlank()) {
            atualizacao.append("medicoId", new ObjectId(novoMedicoId));
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        hospitalMedicoCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Relação médico-hospital atualizada com sucesso.");
    }

    public void deletarMedicoXHospital() {
        // Buscar todas as relações
        List<HospitalMedico> relacoes = hospitalMedicoRepository.buscarTodasRelacoes();
        if (relacoes.isEmpty()) {
            System.out.println("Nenhuma relação entre médicos e hospitais encontrada para exclusão.");
            return;
        }

        // Exibir as relações para o usuário
        System.out.println("Selecione a relação médico-hospital que deseja excluir:");
        for (int i = 0; i < relacoes.size(); i++) {
            HospitalMedico relacao = relacoes.get(i);
            System.out.println((i + 1) + " - Médico: " + relacao.getMedico().getNome() +
                    " | Hospital: " + relacao.getHospital().getRazaoSocial());
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + relacoes.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= relacoes.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar a relação e excluir
        HospitalMedico relacaoSelecionada = relacoes.get(escolha - 1);
        hospitalMedicoRepository.excluirRelacao(relacaoSelecionada.getId());
        System.out.println("Relação médico-hospital excluída com sucesso.");
    }

}
