package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Endereco;

import model.Paciente;

import static com.mongodb.client.model.Filters.eq;

public class PacienteRepository {

    private final MongoCollection<Document> colecao;

    public PacienteRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("pacientes");
    }

    public List<Paciente> buscarTodosPacientes() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Paciente> pacientes = new ArrayList<>();
        EnderecoRepository enderecoRepository = new EnderecoRepository();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Endereco endereco = enderecoRepository.buscarPorCep(doc.getString("endereco.cep"));
            pacientes.add(new Paciente(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getString("email"),
                    doc.getString("telefone"),
                    doc.getString("dataNascimento"),
                    doc.getString("cpf"),
                    endereco // Passar o objeto Endereco
            ));
        }
        cursor.close();
        return pacientes;
    }

    public Paciente buscarPorCpf(String cpf) {
        Bson filtro = eq("cpf", cpf);
        Document doc = colecao.find(filtro).first();
        EnderecoRepository enderecoRepository = new EnderecoRepository();

        if (doc != null) {
            Endereco endereco = enderecoRepository.buscarPorCep(doc.getString("endereco.cep"));

            return new Paciente(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getString("email"),
                    doc.getString("telefone"),
                    doc.getString("dataNascimento"),
                    doc.getString("cpf"),
                    endereco // Passar o objeto Endereco
            );
        }
        return null;
    }

    public void inserirPaciente(Paciente paciente) {
        Document documento = new Document("nome", paciente.getNome())
                .append("email", paciente.getEmail())
                .append("telefone", paciente.getTelefone())
                .append("dataNascimento", paciente.getDataNascimento())
                .append("cpf", paciente.getCpf())
                .append("enderecoId", paciente.getEndereco().getId()); // Armazena o ID do endereço
        colecao.insertOne(documento);
        System.out.println("Paciente inserido com sucesso!");
    }

    public void atualizarPaciente(String id, Paciente pacienteAtualizado) {
        Bson filtro = eq("_id", id);
        Document atualizacao = new Document("$set", new Document("nome", pacienteAtualizado.getNome())
                .append("email", pacienteAtualizado.getEmail())
                .append("telefone", pacienteAtualizado.getTelefone())
                .append("dataNascimento", pacienteAtualizado.getDataNascimento())
                .append("cpf", pacienteAtualizado.getCpf())
                .append("enderecoId", pacienteAtualizado.getEndereco().getId())); // Referência ao endereço
        colecao.updateOne(filtro, atualizacao);
        System.out.println("Paciente atualizado com sucesso!");
    }

    public void excluirPaciente(String id) {
        Bson filtro = eq("_id", id);
        colecao.deleteOne(filtro);
        System.out.println("Paciente excluído com sucesso!");
    }
}
