package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.EspecialidadeMedico;

import static com.mongodb.client.model.Filters.eq;

public class EspecialidadeMedicoRepository {

    private final MongoCollection<Document> colecao;

    public EspecialidadeMedicoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("especialidade_medico");
    }

    public List<EspecialidadeMedico> buscarTodasRelacoes() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<EspecialidadeMedico> relacoes = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            relacoes.add(new EspecialidadeMedico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("medicoId"),
                    doc.getString("especialidadeId")
            ));
        }
        cursor.close();
        return relacoes;
    }

    public EspecialidadeMedico buscarPorMedicoId(String medicoId) {
        Bson filtro = eq("medicoId", medicoId); 
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new EspecialidadeMedico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("medicoId"),
                    doc.getString("especialidadeId")
            );
        }
        return null;
    }

    public void inserirRelacao(EspecialidadeMedico relacao) {
        Document documento = new Document("medicoId", relacao.getMedicoId())
                .append("especialidadeId", relacao.getEspecialidadeId());
        colecao.insertOne(documento);
        System.out.println("Relação entre médico e especialidade inserida com sucesso!");
    }

    public void excluirRelacao(String id) {
        Bson filtro = eq("_id", id); 
        colecao.deleteOne(filtro);
        System.out.println("Relação excluída com sucesso!");
    }
}
