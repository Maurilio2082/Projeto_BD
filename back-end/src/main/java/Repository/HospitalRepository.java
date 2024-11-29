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

            String id = doc.getObjectId("_id").toString();
            String razaoSocial = doc.getString("razaoSocial");
            String cnpj = doc.getString("cnpj");
            String email = doc.getString("email");
            String telefone = doc.getString("telefone");
            String categoria = doc.getString("categoria");

            // Verificar o tipo de dado no campo enderecoId
            String enderecoId = null;
            if (doc.get("enderecoId") instanceof ObjectId) {
                enderecoId = doc.getObjectId("enderecoId").toString();
            } else if (doc.get("enderecoId") instanceof String) {
                enderecoId = doc.getString("enderecoId");
            }

            Endereco endereco = null;
            if (enderecoId != null) {
                endereco = enderecoRepository.buscarPorId(enderecoId);
            }

            hospitais.add(new Hospital(id, razaoSocial, cnpj, email, telefone, categoria, endereco));
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

    public Hospital buscarPorId(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id));
            Document doc = colecao.find(filtro).first();

            if (doc != null) {
                String razaoSocial = doc.getString("razaoSocial");
                String cnpj = doc.getString("cnpj");
                String email = doc.getString("email");
                String telefone = doc.getString("telefone");
                String categoria = doc.getString("categoria");

                String enderecoId = doc.get("enderecoId") != null
                        ? doc.get("enderecoId").toString()
                        : null;

                Endereco endereco = null;
                if (enderecoId != null) {
                    endereco = enderecoRepository.buscarPorId(enderecoId);
                }

                return new Hospital(
                        doc.getObjectId("_id").toHexString(),
                        razaoSocial,
                        cnpj,
                        email,
                        telefone,
                        categoria,
                        endereco);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ID do hospital inválido: " + id);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao buscar hospital por ID: " + e.getMessage());
            e.printStackTrace();
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
    }

    public void atualizarHospital(String id, Hospital hospitalAtualizado) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Certificar que o ID está no formato correto

            // Garantir que o ID do endereço seja armazenado como ObjectId
            ObjectId enderecoObjectId = new ObjectId(hospitalAtualizado.getEndereco().getId());

            // Construir o documento de atualização
            Document atualizacao = new Document("$set", new Document()
                    .append("razaoSocial", hospitalAtualizado.getRazaoSocial())
                    .append("cnpj", hospitalAtualizado.getCnpj())
                    .append("email", hospitalAtualizado.getEmail())
                    .append("telefone", hospitalAtualizado.getTelefone())
                    .append("categoria", hospitalAtualizado.getCategoria())
                    .append("enderecoId", enderecoObjectId)); // Salva o ID do endereço no formato correto

            // Atualizar no MongoDB
            colecao.updateOne(filtro, atualizacao);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar hospital no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void excluirHospital(String hospitalId) {
        try {
            Bson filtro = eq("_id", new ObjectId(hospitalId));
            colecao.deleteOne(filtro);
        } catch (Exception e) {
            System.err.println("Erro ao excluir hospital: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
