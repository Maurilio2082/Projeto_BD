package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Especialidade;
import utils.RemoverDependencia;

import static com.mongodb.client.model.Filters.eq;

public class EspecialidadeRepository {

    private final MongoCollection<Document> colecao;

    public EspecialidadeRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("especialidades");
    }

    public List<Especialidade> buscarTodasEspecialidades() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Especialidade> especialidades = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            especialidades.add(new Especialidade(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nomeEspecialidade")));
        }
        cursor.close();
        return especialidades;
    }

    public Especialidade buscarPorId(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id));
            Document doc = colecao.find(filtro).first();

            if (doc != null) {
                return new Especialidade(
                        doc.getObjectId("_id").toString(),
                        doc.getString("nomeEspecialidade"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar especialidade por ID: " + e.getMessage());
        }
        return null;
    }

    public Especialidade buscarPorNome(String nome) {
        Bson filtro = eq("nomeEspecialidade", nome);
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new Especialidade(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nomeEspecialidade"));
        }
        return null;
    }

    public void inserirEspecialidade(Especialidade especialidade) {
        Document documento = new Document("nomeEspecialidade", especialidade.getNomeEspecialidade());
        colecao.insertOne(documento);
        System.out.println("Especialidade inserida com sucesso!");
    }

    public void atualizarEspecialidade(String id, String novoNome) {
        try {
            // Converter o ID para ObjectId para o MongoDB
            Bson filtro = eq("_id", new ObjectId(id));

            // Definir os campos a serem atualizados
            Document atualizacao = new Document("$set", new Document("nomeEspecialidade", novoNome));

            // Executar a atualização
            colecao.updateOne(filtro, atualizacao);
            System.out.println("Especialidade atualizada com sucesso no banco de dados!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar a especialidade no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void excluirEspecialidade(String especialidadeId) {
        // Remove todas as dependências da especialidade
        RemoverDependencia.removerDependenciasEspecialidade(especialidadeId);

        // Exclui a especialidade da coleção principal
        MongoCollection<Document> especialidadeCollection = DatabaseConfig.getDatabase()
                .getCollection("especialidades");
        especialidadeCollection.deleteOne(eq("_id", new ObjectId(especialidadeId)));

        System.out.println("Especialidade excluída com sucesso!");
    }

    public Especialidade buscarEspecialidadePorId(String id) {
        Bson filtro = eq("_id", new ObjectId(id));
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new Especialidade(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nomeEspecialidade"));
        }
        return null;
    }

}
