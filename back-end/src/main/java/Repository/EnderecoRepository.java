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

import static com.mongodb.client.model.Filters.eq;

public class EnderecoRepository {

    private final MongoCollection<Document> colecao;

    public EnderecoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("enderecos");
    }

    public List<Endereco> buscarTodosEnderecos() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Endereco> enderecos = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            enderecos.add(new Endereco(
                    doc.getObjectId("_id").toString(),
                    doc.getString("logradouro"),
                    doc.getString("numero"),
                    doc.getString("bairro"),
                    doc.getString("cidade"),
                    doc.getString("estado"),
                    doc.getString("cep")));
        }
        cursor.close();
        return enderecos;
    }

    public Endereco buscarPorId(String id) {
        if (id == null || id.isEmpty() || id.length() != 24) {
            System.err.println("ID do endereço inválido ou ausente: " + id);
            return null;
        }

        try {
            Bson filtro = eq("_id", new ObjectId(id));
            Document doc = colecao.find(filtro).first();

            if (doc != null) {
                return new Endereco(
                        doc.getObjectId("_id").toString(),
                        doc.getString("logradouro"),
                        doc.getString("numero"),
                        doc.getString("bairro"),
                        doc.getString("cidade"),
                        doc.getString("estado"),
                        doc.getString("cep"));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ID do endereço inválido: " + id);
            e.printStackTrace();
        }
        return null;
    }

    public String inserirEndereco(Endereco endereco) {
        Document documento = new Document("logradouro", endereco.getLogradouro())
                .append("numero", endereco.getNumero())
                .append("bairro", endereco.getBairro())
                .append("cidade", endereco.getCidade())
                .append("estado", endereco.getEstado())
                .append("cep", endereco.getCep());
        colecao.insertOne(documento);
        System.out.println("Endereço inserido com sucesso!");
        return documento.getObjectId("_id").toString(); // Retorna o ID gerado pelo MongoDB
    }

    public void atualizarEndereco(String id, Endereco enderecoAtualizado) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Certifique-se de usar ObjectId
            Document atualizacao = new Document("$set", new Document()
                    .append("logradouro", enderecoAtualizado.getLogradouro())
                    .append("numero", enderecoAtualizado.getNumero())
                    .append("bairro", enderecoAtualizado.getBairro())
                    .append("cidade", enderecoAtualizado.getCidade())
                    .append("estado", enderecoAtualizado.getEstado())
                    .append("cep", enderecoAtualizado.getCep()));

            colecao.updateOne(filtro, atualizacao);

            System.out.println("Endereço atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o endereço no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void excluirEndereco(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id));
            colecao.deleteOne(filtro);
            System.out.println("Endereço excluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir endereço: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
