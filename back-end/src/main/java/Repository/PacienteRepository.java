package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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

            String id = doc.getObjectId("_id").toString(); // ID principal
            String nome = doc.getString("nome");
            String email = doc.getString("email");
            String telefone = doc.getString("telefone");
            String dataNascimento = doc.getString("dataNascimento");
            String cpf = doc.getString("cpf");

            // Recuperar o endereço associado
            Endereco endereco = null;
            if (doc.get("enderecoId") instanceof ObjectId) {
                String enderecoId = doc.getObjectId("enderecoId").toString();
                endereco = enderecoRepository.buscarPorId(enderecoId);
            }

            Paciente paciente = new Paciente(id, nome, email, telefone, dataNascimento, cpf, endereco);
            pacientes.add(paciente);
        }
        cursor.close();
        return pacientes;
    }

    public Paciente buscarPorCpf(String cpf) {
        Bson filtro = eq("cpf", cpf);
        Document doc = colecao.find(filtro).first();
        EnderecoRepository enderecoRepository = new EnderecoRepository();

        if (doc != null) {
            Endereco endereco = enderecoRepository.buscarPorId(doc.getString("endereco._id"));

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
    }

    public void atualizarPaciente(String id, Paciente pacienteAtualizado) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Garantir que o ID seja um ObjectId válido

            // Garante que o endereço seja armazenado corretamente como ObjectId
            ObjectId enderecoObjectId = new ObjectId(pacienteAtualizado.getEndereco().getId());

            Document atualizacao = new Document("$set", new Document()
                    .append("nome", pacienteAtualizado.getNome())
                    .append("email", pacienteAtualizado.getEmail())
                    .append("telefone", pacienteAtualizado.getTelefone())
                    .append("dataNascimento", pacienteAtualizado.getDataNascimento())
                    .append("cpf", pacienteAtualizado.getCpf())
                    .append("enderecoId", enderecoObjectId)); // Garante que o ID do endereço seja salvo corretamente

            colecao.updateOne(filtro, atualizacao);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar paciente no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void excluirPaciente(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Certifique-se de usar ObjectId para IDs no MongoDB
            colecao.deleteOne(filtro);
        } catch (Exception e) {
            System.err.println("Erro ao excluir paciente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Paciente buscarPorId(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id));
            Document doc = colecao.find(filtro).first();
            EnderecoRepository enderecoRepository = new EnderecoRepository();

            if (doc != null) {
                String enderecoId = doc.get("enderecoId") != null
                        ? doc.get("enderecoId").toString()
                        : null;

                Endereco endereco = null;
                if (enderecoId != null) {
                    endereco = enderecoRepository.buscarPorId(enderecoId);
                }

                return new Paciente(
                        doc.getObjectId("_id").toHexString(),
                        doc.getString("nome"),
                        doc.getString("email"),
                        doc.getString("telefone"),
                        doc.getString("dataNascimento"),
                        doc.getString("cpf"),
                        endereco);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ID do paciente inválido: " + id);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao buscar paciente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
