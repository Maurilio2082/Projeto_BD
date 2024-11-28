package controller;

import model.Endereco;

import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import Repository.EnderecoRepository;
import conexion.DatabaseConfig;

public class EnderecoController {

    private final EnderecoRepository repository;
    private final MongoCollection<Document> enderecoCollection;

    private final Scanner scanner;
    private String ultimoEnderecoCadastradoId; // Para armazenar o ID do último endereço cadastrado.

    public EnderecoController() {
        this.enderecoCollection = DatabaseConfig.getDatabase().getCollection("enderecos");

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
    
        System.out.print("Logradouro [" + enderecoAtual.getLogradouro() + "]: ");
        String logradouro = scanner.nextLine();
    
        System.out.print("Número [" + enderecoAtual.getNumero() + "]: ");
        String numero = scanner.nextLine();
    
        System.out.print("Bairro [" + enderecoAtual.getBairro() + "]: ");
        String bairro = scanner.nextLine();
    
        System.out.print("Cidade [" + enderecoAtual.getCidade() + "]: ");
        String cidade = scanner.nextLine();
    
        System.out.print("Estado [" + enderecoAtual.getEstado() + "]: ");
        String estado = scanner.nextLine();
    
        System.out.print("CEP [" + enderecoAtual.getCep() + "]: ");
        String cep = scanner.nextLine();
    
        // Criar um objeto Endereco atualizado com os novos dados ou manter os existentes
        Endereco enderecoAtualizado = new Endereco(
                enderecoAtual.getId(),
                logradouro.isEmpty() ? enderecoAtual.getLogradouro() : logradouro,
                numero.isEmpty() ? enderecoAtual.getNumero() : numero,
                bairro.isEmpty() ? enderecoAtual.getBairro() : bairro,
                cidade.isEmpty() ? enderecoAtual.getCidade() : cidade,
                estado.isEmpty() ? enderecoAtual.getEstado() : estado,
                cep.isEmpty() ? enderecoAtual.getCep() : cep
        );
    
        // Persistir alterações no banco de dados
        try {
            repository.atualizarEndereco(enderecoAtualizado.getId(), enderecoAtualizado);
            System.out.println("Endereço atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o endereço no banco de dados: " + e.getMessage());
        }
    
        return enderecoAtualizado;
    }
    

    public void excluirEndereco(String enderecoId) {
        repository.excluirEndereco(enderecoId);
        System.out.println("Endereço excluído com sucesso!");
    }

}
