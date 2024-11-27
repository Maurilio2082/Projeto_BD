package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

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
                    doc.getString("cep")
            ));
        }
        cursor.close();
        return enderecos;
    }

    public Endereco buscarPorCep(String cep) {
        Bson filtro = eq("cep", cep); 
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new Endereco(
                    doc.getObjectId("_id").toString(),
                    doc.getString("logradouro"),
                    doc.getString("numero"),
                    doc.getString("bairro"),
                    doc.getString("cidade"),
                    doc.getString("estado"),
                    doc.getString("cep")
            );
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
        Bson filtro = eq("_id", id); 
        Document atualizacao = new Document("$set", new Document("logradouro", enderecoAtualizado.getLogradouro())
                .append("numero", enderecoAtualizado.getNumero())
                .append("bairro", enderecoAtualizado.getBairro())
                .append("cidade", enderecoAtualizado.getCidade())
                .append("estado", enderecoAtualizado.getEstado())
                .append("cep", enderecoAtualizado.getCep()));
        colecao.updateOne(filtro, atualizacao);
        System.out.println("Endereço atualizado com sucesso!");
    }

    public void excluirEndereco(String id) {
        Bson filtro = eq("_id", id); 
        colecao.deleteOne(filtro);
        System.out.println("Endereço excluído com sucesso!");
    }
}
