package controller;

import com.mongodb.client.MongoCollection;
import conexion.DatabaseConfig;
import model.Especialidade;
import model.EspecialidadeMedico;
import model.Medico;
import Repository.EspecialidadeRepository;
import Repository.MedicoRepository;
import Repository.EspecialidadeMedicoRepository;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

public class EspecialidadeMedicoController {

    private final EspecialidadeRepository especialidadeRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeMedicoRepository especialidadeMedicoRepository;
    private final MongoCollection<Document> especialidadeMedicoCollection;
    private final Scanner scanner;

    public EspecialidadeMedicoController() {
        this.especialidadeRepository = new EspecialidadeRepository();
        this.medicoRepository = new MedicoRepository();
        this.especialidadeMedicoRepository = new EspecialidadeMedicoRepository();
        this.especialidadeMedicoCollection = DatabaseConfig.getDatabase().getCollection("especialidades_medicos");
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarEspecialidadeXMedico() {
        // Listar médicos disponíveis
        List<Medico> medicos = medicoRepository.buscarTodosMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
            return;
        }

        System.out.println("Selecione o médico:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println((i + 1) + " - " + medico.getNome());
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

        // Listar especialidades disponíveis
        List<Especialidade> especialidades = especialidadeRepository.buscarTodasEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada.");
            return;
        }

        System.out.println("Selecione a especialidade:");
        for (int i = 0; i < especialidades.size(); i++) {
            Especialidade especialidade = especialidades.get(i);
            System.out.println((i + 1) + " - " + especialidade.getNomeEspecialidade());
        }

        int escolhaEspecialidade;
        while (true) {
            System.out.print("Escolha o número da especialidade: ");
            try {
                escolhaEspecialidade = Integer.parseInt(scanner.nextLine());
                if (escolhaEspecialidade >= 1 && escolhaEspecialidade <= especialidades.size()) {
                    break;
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        Especialidade especialidadeEscolhida = especialidades.get(escolhaEspecialidade - 1);

        // Relacionar médico e especialidade
        EspecialidadeMedico relacao = new EspecialidadeMedico(medicoEscolhido, especialidadeEscolhida);
        especialidadeMedicoRepository.inserirRelacao(relacao);
        System.out.println("Relacionamento entre médico e especialidade cadastrado com sucesso!");
    }

    public void atualizarEspecialidadeXMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação especialidade-médico que deseja atualizar: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        Document relacaoAtual = especialidadeMedicoCollection.find(filtro).first();

        if (relacaoAtual == null) {
            System.out.println("Relação não encontrada.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Novo ID da especialidade: ");
        String novaEspecialidadeId = scanner.nextLine();
        System.out.print("Novo ID do médico: ");
        String novoMedicoId = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novaEspecialidadeId.isBlank()) {
            atualizacao.append("especialidadeId", new ObjectId(novaEspecialidadeId));
        }
        if (!novoMedicoId.isBlank()) {
            atualizacao.append("medicoId", new ObjectId(novoMedicoId));
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        especialidadeMedicoCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Relação especialidade-médico atualizada com sucesso.");
    }

    public void deletarEspecialidadeXMedico() {
        // Buscar todas as relações de especialidades com médicos
        List<EspecialidadeMedico> relacoes = especialidadeMedicoRepository.buscarTodasRelacoes();
        if (relacoes.isEmpty()) {
            System.out.println("Nenhuma relação entre médicos e especialidades encontrada para exclusão.");
            return;
        }
    
        // Exibir as relações disponíveis
        System.out.println("Selecione a relação que deseja excluir:");
        for (int i = 0; i < relacoes.size(); i++) {
            EspecialidadeMedico relacao = relacoes.get(i);
            System.out.println((i + 1) + " - Médico: " + relacao.getMedico().getNome() +
                    ", Especialidade: " + relacao.getEspecialidade().getNomeEspecialidade());
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
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    
        // Selecionar a relação
        EspecialidadeMedico relacaoSelecionada = relacoes.get(escolha - 1);
    
        // Excluir a relação do repositório
        especialidadeMedicoRepository.excluirRelacao(relacaoSelecionada.getId());
        System.out.println("Relação entre médico e especialidade excluída com sucesso!");
    }
    
}
