package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Especialidade;
import model.Historico;
import model.Hospital;
import model.Medico;
import model.Paciente;

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

            Paciente paciente = new Paciente(
                    doc.getObjectId("pacienteId").toString(),
                    null, null, null, null, null, null);

            Hospital hospital = new Hospital(
                    doc.getObjectId("hospitalId").toString(),
                    null, null, null, null, null, null);

            Medico medico = new Medico(
                    doc.getObjectId("medicoId").toString(),
                    null, null, null);

            Especialidade especialidade = new Especialidade(
                    doc.getObjectId("especialidadeId").toString(),
                    null);

            historicos.add(new Historico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("dataConsulta"),
                    doc.getString("observacao"),
                    paciente,
                    hospital,
                    medico,
                    especialidade));
        }
        cursor.close();
        return historicos;
    }

    public Historico buscarPorId(String id) {
        Bson filtro = eq("_id", new ObjectId(id));
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            Paciente paciente = new Paciente(
                    doc.getObjectId("pacienteId").toString(),
                    null, null, null, null, null, null);

            Hospital hospital = new Hospital(
                    doc.getObjectId("hospitalId").toString(),
                    null, null, null, null, null, null);

            Medico medico = new Medico(
                    doc.getObjectId("medicoId").toString(),
                    null, null, null);

            Especialidade especialidade = new Especialidade(
                    doc.getObjectId("especialidadeId").toString(),
                    null);

            return new Historico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("dataConsulta"),
                    doc.getString("observacao"),
                    paciente,
                    hospital,
                    medico,
                    especialidade);
        }
        return null;
    }

    public void inserirHistorico(Historico historico) {
        Document documento = new Document("dataConsulta", historico.getDataConsulta())
                .append("observacao", historico.getObservacao())
                .append("pacienteId", new ObjectId(historico.getPaciente().getId()))
                .append("hospitalId", new ObjectId(historico.getHospital().getId()))
                .append("medicoId", new ObjectId(historico.getMedico().getId()))
                .append("especialidadeId", new ObjectId(historico.getEspecialidade().getId()));

        colecao.insertOne(documento);
        System.out.println("Histórico inserido com sucesso!");
    }

    public void excluirHistorico(String id) {
        Bson filtro = eq("_id", id);
        colecao.deleteOne(filtro);
        System.out.println("Histórico excluído com sucesso!");
    }
}
