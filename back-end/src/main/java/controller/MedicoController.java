package controller;

import model.Especialidade;
import model.Medico;
import utils.RemoverDependencia;

import java.util.List;
import java.util.Scanner;

import Repository.EspecialidadeRepository;
import Repository.MedicoRepository;

public class MedicoController {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final Scanner scanner;

    public MedicoController() {
        this.medicoRepository = new MedicoRepository();
        this.especialidadeRepository = new EspecialidadeRepository();
        this.scanner = new Scanner(System.in);
    }

    public void listarMedicos() {
        List<Medico> medicos = medicoRepository.buscarTodosMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
        } else {
            medicos.forEach(System.out::println);
        }
    }

    public void buscarMedicoPorNome() {
        System.out.print("Digite o nome do médico: ");
        String nome = scanner.nextLine();
        Medico medico = medicoRepository.buscarPorNome(nome);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
        } else {
            System.out.println(medico);
        }
    }

    public void cadastrarMedico() {
        System.out.println("Digite os dados do novo médico:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Conselho: ");
        String conselho = scanner.nextLine();

        // Listar especialidades disponíveis
        List<Especialidade> especialidades = especialidadeRepository.buscarTodasEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada. Não é possível cadastrar o médico.");
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

        // Cria e insere o médico
        Medico medico = new Medico(null, nome, conselho, especialidadeEscolhida);
        medicoRepository.inserirMedico(medico);
    }

    public void atualizarMedico() {
        System.out.print("Digite o ID do médico que deseja atualizar: ");
        String id = scanner.nextLine();

        // Buscar o médico atual para exibir seus dados
        Medico medicoAtual = medicoRepository.buscarPorNome(id);
        if (medicoAtual == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.println("Atualize os dados (ou deixe em branco para manter o atual):");

        System.out.print("Nome [" + medicoAtual.getNome() + "]: ");
        String nome = scanner.nextLine();

        System.out.print("Conselho [" + medicoAtual.getConselho() + "]: ");
        String conselho = scanner.nextLine();

        System.out.println("Especialidade atual: " + medicoAtual.getEspecialidade().getNomeEspecialidade());
        System.out.println("Deseja alterar a especialidade? (Sim/Não)");
        String alterarEspecialidade = scanner.nextLine().trim().toLowerCase();

        Especialidade especialidadeAtualizada = medicoAtual.getEspecialidade(); // Manter a atual por padrão
        if (alterarEspecialidade.equals("sim")) {
            // Listar especialidades disponíveis
            List<Especialidade> especialidades = especialidadeRepository.buscarTodasEspecialidades();
            if (especialidades.isEmpty()) {
                System.out.println("Nenhuma especialidade encontrada. Não é possível alterar a especialidade.");
                return;
            }

            System.out.println("Selecione a nova especialidade:");
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
                        especialidadeAtualizada = especialidades.get(escolhaEspecialidade - 1);
                        break;
                    } else {
                        System.out.println("Número inválido. Tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                }
            }
        }

        // Cria o objeto atualizado com os novos dados ou mantém os existentes
        Medico medicoAtualizado = new Medico(
                id,
                nome.isEmpty() ? medicoAtual.getNome() : nome,
                conselho.isEmpty() ? medicoAtual.getConselho() : conselho,
                especialidadeAtualizada);

        // Atualiza no repositório
        medicoRepository.atualizarMedico(id, medicoAtualizado);
        System.out.println("Médico atualizado com sucesso!");
    }

    public void deletarMedico() {
        System.out.print("Digite o ID do médico que deseja excluir: ");
        String medicoId = scanner.nextLine();

        System.out.println("Excluindo dependências relacionadas ao médico...");
        RemoverDependencia.removerDependenciasMedico(medicoId);

        medicoRepository.excluirMedico(medicoId);
        System.out.println("Médico excluído com sucesso.");
    }
}
