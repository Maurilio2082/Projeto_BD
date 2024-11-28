package controller;

import model.Medico;

import java.util.List;
import java.util.Scanner;

import Repository.MedicoRepository;

public class MedicoController {

    private final MedicoRepository medicoRepository;
    private final Scanner scanner;

    public MedicoController() {
        this.medicoRepository = new MedicoRepository();
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

        // Cria e insere o médico
        Medico medico = new Medico(null, nome, conselho, null);
        medicoRepository.inserirMedico(medico);
        System.out.println("Médico cadastrado com sucesso!");
    }

    public void atualizarMedico() {
        // Listar todos os médicos
        List<Medico> medicos = medicoRepository.buscarTodosMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado para atualização.");
            return;
        }

        // Exibir a lista de médicos
        System.out.println("Selecione o médico que deseja atualizar:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println((i + 1) + " - " + medico.getNome() + " (Conselho: " + medico.getConselho() + ")");
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + medicos.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= medicos.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar o médico
        Medico medicoAtual = medicos.get(escolha - 1);

        // Exibir os dados atuais e permitir a edição
        System.out.println("Atualize os dados do médico (ou deixe em branco para manter o atual):");

        System.out.print("Nome [" + medicoAtual.getNome() + "]: ");
        String nome = scanner.nextLine();

        System.out.print("Conselho [" + medicoAtual.getConselho() + "]: ");
        String conselho = scanner.nextLine();

        // Criar objeto atualizado com os novos dados ou manter os existentes
        Medico medicoAtualizado = new Medico(
                medicoAtual.getId(),
                nome.isEmpty() ? medicoAtual.getNome() : nome,
                conselho.isEmpty() ? medicoAtual.getConselho() : conselho,
                medicoAtual.getEspecialidade()); // Mantém a especialidade existente

        // Atualizar no repositório
        medicoRepository.atualizarMedico(medicoAtual.getId(), medicoAtualizado);
        System.out.println("Médico atualizado com sucesso!");
    }

    public void deletarMedico() {
        // Listar todos os médicos
        List<Medico> medicos = medicoRepository.buscarTodosMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico encontrado para exclusão.");
            return;
        }

        // Exibir a lista de médicos
        System.out.println("Selecione o médico que deseja excluir:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println((i + 1) + " - " + medico.getNome() + " (Conselho: " + medico.getConselho() + ")");
        }

        // Obter a escolha do usuário
        int escolha;
        while (true) {
            System.out.print("Escolha uma opção [1-" + medicos.size() + "]: ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= medicos.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        // Selecionar o médico
        Medico medicoSelecionado = medicos.get(escolha - 1);

        // Excluir associações relacionadas ao médico
        System.out.println("Excluindo associações relacionadas ao médico...");
        medicoRepository.removerDependenciasMedico(medicoSelecionado.getId());

        // Excluir o médico
        medicoRepository.excluirMedico(medicoSelecionado.getId());
        System.out.println("Médico excluído com sucesso!");
    }

}
