package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.HospitalMedico;

import static com.mongodb.client.model.Filters.eq;

public class HospitalMedicoRepository {

    private final MongoCollection<Document> colecao;

    public HospitalMedicoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("hospital_medico");
    }

    public List<HospitalMedico> buscarTodasRelacoes() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<HospitalMedico> relacoes = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            relacoes.add(new HospitalMedico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("hospitalId"),
                    doc.getString("medicoId")));
        }
        cursor.close();
        return relacoes;
    }

    public HospitalMedico buscarPorHospitalId(String hospitalId) {
        Bson filtro = eq("hospitalId", hospitalId);
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new HospitalMedico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("hospitalId"),
                    doc.getString("medicoId"));
        }
        return null;
    }

    public void inserirRelacao(HospitalMedico relacao) {
        Document documento = new Document("hospitalId", relacao.getHospitalId())
                .append("medicoId", relacao.getMedicoId());
        colecao.insertOne(documento);
        System.out.println("Relação entre hospital e médico inserida com sucesso!");
    }

    public void excluirRelacao(String id) {
        Bson filtro = eq("_id", id);
        colecao.deleteOne(filtro);
        System.out.println("Relação excluída com sucesso!");
    }
}
