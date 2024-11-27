package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

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
                .append("conselho", medico.getConselho())
                .append("especialidadeId", medico.getEspecialidade().getId()); // Salva apenas o ID da especialidade
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
}
