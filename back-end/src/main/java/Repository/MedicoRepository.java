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
import model.Medico;

import static com.mongodb.client.model.Filters.eq;

public class MedicoRepository {

    private final MongoCollection<Document> colecao;
    private final EspecialidadeRepository especialidadeRepository;

    public MedicoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("medicos");
        this.especialidadeRepository = new EspecialidadeRepository();
    }

    public List<Medico> buscarTodosMedicos() {
        MongoCursor<Document> cursor = colecao.find().iterator();
        List<Medico> medicos = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String especialidadeId = doc.getString("especialidadeId");
            Especialidade especialidade = especialidadeRepository.buscarPorId(especialidadeId);

            medicos.add(new Medico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getString("conselho"),
                    especialidade));
        }
        cursor.close();
        return medicos;
    }

    public Medico buscarPorNome(String nome) {
        Bson filtro = eq("nome", nome);
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            String especialidadeId = doc.getString("especialidadeId");
            Especialidade especialidade = especialidadeRepository.buscarPorId(especialidadeId);

            return new Medico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getString("conselho"),
                    especialidade);
        }
        return null;
    }

    public void inserirMedico(Medico medico) {
        Document documento = new Document("nome", medico.getNome())
                .append("conselho", medico.getConselho());
        colecao.insertOne(documento);
        System.out.println("Médico inserido com sucesso!");
    }

    public void atualizarMedico(String id, Medico medicoAtualizado) {
        Bson filtro = eq("_id", id);
        Document atualizacao = new Document("$set", new Document("nome", medicoAtualizado.getNome())
                .append("conselho", medicoAtualizado.getConselho())
                .append("especialidadeId", medicoAtualizado.getEspecialidade().getId())); // Salva apenas o ID da
                                                                                          // especialidade
        colecao.updateOne(filtro, atualizacao);
        System.out.println("Médico atualizado com sucesso!");
    }

    public void excluirMedico(String id) {
        Bson filtro = eq("_id", id);
        colecao.deleteOne(filtro);
        System.out.println("Médico excluído com sucesso!");
    }

    public List<Medico> buscarMedicosPorHospital(String hospitalId) {
        MongoCollection<Document> colecaoHospitalMedico = DatabaseConfig.getDatabase()
                .getCollection("hospitais_medicos");

        // Busca os médicos associados ao hospital
        List<Medico> medicos = new ArrayList<>();
        Bson filtro = eq("hospitalId", new ObjectId(hospitalId));
        MongoCursor<Document> cursor = colecaoHospitalMedico.find(filtro).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String medicoId = doc.getObjectId("medicoId").toString();

            // Busca o médico pelo ID
            Medico medico = buscarPorId(medicoId);
            if (medico != null) {
                medicos.add(medico);
            }
        }
        cursor.close();
        return medicos;
    }

    public Medico buscarPorId(String id) {
        Bson filtro = eq("_id", new ObjectId(id));
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            return new Medico(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getString("conselho"),
                    null // Se necessário, passe null para especialidade, pois será configurada em outro
                         // momento
            );
        }
        return null;
    }

}
