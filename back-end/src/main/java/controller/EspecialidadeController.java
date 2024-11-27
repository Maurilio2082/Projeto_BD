package controller;

import java.util.List;
import java.util.Scanner;

import Repository.EspecialidadeRepository;
import model.Especialidade;
import utils.RemoverDependencia;

public class EspecialidadeController {

    private final EspecialidadeRepository repository;
    private final Scanner scanner;

    public EspecialidadeController() {
        this.repository = new EspecialidadeRepository();
        this.scanner = new Scanner(System.in);
    }

    public void listarEspecialidades() {
        List<Especialidade> especialidades = repository.buscarTodasEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade encontrada.");
        } else {
            especialidades.forEach(System.out::println);
        }
    }

    public void buscarEspecialidadePorId() {
        System.out.print("Digite o ID da especialidade: ");
        String id = scanner.nextLine();
        Especialidade especialidade = repository.buscarPorId(id);
        if (especialidade == null) {
            System.out.println("Especialidade não encontrada.");
        } else {
            System.out.println(especialidade);
        }
    }

    public void buscarEspecialidadePorNome() {
        System.out.print("Digite o nome da especialidade: ");
        String nome = scanner.nextLine();
        Especialidade especialidade = repository.buscarPorNome(nome);
        if (especialidade == null) {
            System.out.println("Especialidade não encontrada.");
        } else {
            System.out.println(especialidade);
        }
    }

    public void cadastrarEspecialidade() {
        System.out.print("Digite o nome da nova especialidade: ");
        String nome = scanner.nextLine();
        Especialidade especialidade = new Especialidade(null, nome);
        repository.inserirEspecialidade(especialidade);
    }

    public void atualizarEspecialidade() {
        System.out.print("Digite o ID da especialidade: ");
        String id = scanner.nextLine();
        Especialidade especialidadeAtual = repository.buscarPorId(id);

        if (especialidadeAtual == null) {
            System.out.println("Especialidade não encontrada.");
            return;
        }

        System.out.print("Digite o novo nome da especialidade (ou deixe em branco para manter o atual): ");
        String novoNome = scanner.nextLine();

        String nomeAtualizado = novoNome.isEmpty() ? especialidadeAtual.getNomeEspecialidade() : novoNome;
        repository.atualizarEspecialidade(id, nomeAtualizado);
    }

    public void deletarEspecialidade() {
        System.out.print("Digite o ID da especialidade que deseja excluir: ");
        String especialidadeId = scanner.nextLine();

        System.out.println("Excluindo dependências relacionadas à especialidade...");
        RemoverDependencia.removerDependenciasEspecialidade(especialidadeId);

        repository.excluirEspecialidade(especialidadeId);
        System.out.println("Especialidade excluída com sucesso.");
    }
}
