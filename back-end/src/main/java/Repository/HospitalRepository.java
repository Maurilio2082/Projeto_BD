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
import model.Hospital;

import static com.mongodb.client.model.Filters.eq;

public class HospitalRepository {

    private final MongoCollection<Document> colecao;
    private final EnderecoRepository enderecoRepository;

    public HospitalRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("hospitais");
        this.enderecoRepository = new EnderecoRepository();
    }

    public List<Hospital> buscarTodosHospitais() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Hospital> hospitais = new ArrayList<>();
        EnderecoRepository enderecoRepository = new EnderecoRepository();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            String enderecoId = doc.getString("enderecoId");
            Endereco endereco = null;

            // Verificar se o enderecoId é válido antes de buscar o endereço
            if (enderecoId != null && enderecoId.length() == 24) {
                endereco = enderecoRepository.buscarPorId(enderecoId);
            } else {
                System.err.println("ID de endereço inválido ou ausente para hospital: " + doc.getString("razaoSocial"));
            }

            hospitais.add(new Hospital(
                    doc.getObjectId("_id").toString(),
                    doc.getString("razaoSocial"),
                    doc.getString("cnpj"),
                    doc.getString("email"),
                    doc.getString("telefone"),
                    doc.getString("categoria"),
                    endereco // Passar o objeto Endereco, pode ser nulo
            ));
        }
        cursor.close();
        return hospitais;
    }

    public Hospital buscarPorCnpj(String cnpj) {
        Bson filtro = eq("cnpj", cnpj);
        Document doc = colecao.find(filtro).first();

        if (doc != null) {

            Endereco endereco = enderecoRepository.buscarPorId(doc.getString("endereco._id"));

            return new Hospital(
                    doc.getObjectId("_id").toString(),
                    doc.getString("razaoSocial"),
                    doc.getString("cnpj"),
                    doc.getString("email"),
                    doc.getString("telefone"),
                    doc.getString("categoria"),
                    endereco);
        }
        return null;
    }

    public void inserirHospital(Hospital hospital) {
        Document documento = new Document("razaoSocial", hospital.getRazaoSocial())
                .append("cnpj", hospital.getCnpj())
                .append("email", hospital.getEmail())
                .append("telefone", hospital.getTelefone())
                .append("categoria", hospital.getCategoria())
                .append("enderecoId", hospital.getEndereco().getId()); // Salvar apenas o ID do endereço
        colecao.insertOne(documento);
        System.out.println("Hospital inserido com sucesso!");
    }

    public void atualizarHospital(String id, Hospital hospitalAtualizado) {
        Bson filtro = eq("_id", id);
        Document atualizacao = new Document("$set", new Document("razaoSocial", hospitalAtualizado.getRazaoSocial())
                .append("cnpj", hospitalAtualizado.getCnpj())
                .append("email", hospitalAtualizado.getEmail())
                .append("telefone", hospitalAtualizado.getTelefone())
                .append("categoria", hospitalAtualizado.getCategoria())
                .append("enderecoId", hospitalAtualizado.getEndereco().getId())); // Salvar apenas o ID do endereço
        colecao.updateOne(filtro, atualizacao);
        System.out.println("Hospital atualizado com sucesso!");
    }

    public void excluirHospital(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Certifique-se de usar ObjectId para IDs no MongoDB
            colecao.deleteOne(filtro);
            System.out.println("Hospital excluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir hospital: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
