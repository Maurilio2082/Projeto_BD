package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Historico;

import static com.mongodb.client.model.Filters.eq;

public class HistoricoRepository {

    private final MongoCollection<Document> colecao;

    public HistoricoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("historicos");
    }

    public List<Historico> buscarTodosHistoricos() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Historico> historicos = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            historicos.add(new Historico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("dataConsulta"),
                    doc.getString("observacao"),
                    doc.getString("pacienteId"),
                    doc.getString("hospitalId"),
                    doc.getString("medicoId"),
                    doc.getString("especialidadeId")
            ));
        }
        cursor.close();
        return historicos;
    }

    public Historico buscarPorId(String id) {
        Bson filtro = eq("_id", id); 
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new Historico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("dataConsulta"),
                    doc.getString("observacao"),
                    doc.getString("pacienteId"),
                    doc.getString("hospitalId"),
                    doc.getString("medicoId"),
                    doc.getString("especialidadeId")
            );
        }
        return null;
    }

    public void inserirHistorico(Historico historico) {
        Document documento = new Document("dataConsulta", historico.getDataConsulta())
                .append("observacao", historico.getObservacao())
                .append("pacienteId", historico.getPacienteId())
                .append("hospitalId", historico.getHospitalId())
                .append("medicoId", historico.getMedicoId())
                .append("especialidadeId", historico.getEspecialidadeId());
        colecao.insertOne(documento);
        System.out.println("Histórico inserido com sucesso!");
    }

    public void excluirHistorico(String id) {
        Bson filtro = eq("_id", id); 
        colecao.deleteOne(filtro);
        System.out.println("Histórico excluído com sucesso!");
    }
}
