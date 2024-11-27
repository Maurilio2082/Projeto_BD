package controller;

import model.Endereco;

import java.util.List;
import java.util.Scanner;

import Repository.EnderecoRepository;

public class EnderecoController {

    private final EnderecoRepository repository;
    private final Scanner scanner;
    private String ultimoEnderecoCadastradoId; // Para armazenar o ID do último endereço cadastrado.

    public EnderecoController() {
        this.repository = new EnderecoRepository();
        this.scanner = new Scanner(System.in);
    }

    public void listarEnderecos() {
        List<Endereco> enderecos = repository.buscarTodosEnderecos();
        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereço encontrado.");
        } else {
            enderecos.forEach(System.out::println);
        }
    }

    public void buscarEnderecoPorCep() {
        System.out.print("Digite o CEP do endereço: ");
        String cep = scanner.nextLine();
        Endereco endereco = repository.buscarPorCep(cep);
        if (endereco == null) {
            System.out.println("Endereço não encontrado.");
        } else {
            System.out.println(endereco);
        }
    }

    public Endereco cadastrarEndereco() {
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
    
        Endereco endereco = new Endereco(null, logradouro, numero, bairro, cidade, estado, cep);
        String enderecoId = repository.inserirEndereco(endereco);
        endereco.setId(enderecoId); // Atualiza o ID no objeto Endereco
        return endereco;
    }
    

    public String getUltimoEnderecoCadastradoId() {
        return ultimoEnderecoCadastradoId;
    }

    public Endereco atualizarEndereco(Endereco enderecoAtual) {
        System.out.println("Atualize os dados do endereço (ou deixe em branco para manter o atual):");
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        Endereco enderecoAtualizado = new Endereco(
                enderecoAtual.getId(),
                logradouro.isEmpty() ? enderecoAtual.getLogradouro() : logradouro,
                numero.isEmpty() ? enderecoAtual.getNumero() : numero,
                bairro.isEmpty() ? enderecoAtual.getBairro() : bairro,
                cidade.isEmpty() ? enderecoAtual.getCidade() : cidade,
                estado.isEmpty() ? enderecoAtual.getEstado() : estado,
                cep.isEmpty() ? enderecoAtual.getCep() : cep);

        repository.atualizarEndereco(enderecoAtualizado.getId(), enderecoAtualizado);
        return enderecoAtualizado;
    }

    public void excluirEndereco() {
        System.out.print("Digite o ID do endereço que deseja excluir: ");
        String id = scanner.nextLine();
        repository.excluirEndereco(id);
    }
}
