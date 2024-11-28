package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import conexion.DatabaseConfig;
import model.Hospital;
import model.HospitalMedico;
import model.Medico;

import static com.mongodb.client.model.Filters.eq;

public class HospitalMedicoRepository {

    private final MongoCollection<Document> colecao;
    private final HospitalRepository hospitalRepository;
    private final MedicoRepository medicoRepository;

    public HospitalMedicoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("hospitais_medicos");
        this.hospitalRepository = new HospitalRepository();
        this.medicoRepository = new MedicoRepository();
    }

    public List<HospitalMedico> buscarTodasRelacoes() {
        List<HospitalMedico> relacoes = new ArrayList<>();
        MongoCursor<Document> cursor = colecao.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Hospital hospital = hospitalRepository.buscarPorCnpj(doc.getObjectId("cnpj").toString());
            Medico medico = medicoRepository.buscarPorNome(doc.getObjectId("nome").toString());

            if (hospital != null && medico != null) {
                relacoes.add(new HospitalMedico(doc.getObjectId("_id").toString(), hospital, medico));
            }
        }
        cursor.close();
        return relacoes;
    }

    public HospitalMedico buscarPorHospitalId(String hospitalId) {
        Document doc = colecao.find(eq("hospitalId", new ObjectId(hospitalId))).first();

        if (doc != null) {
            Hospital hospital = hospitalRepository.buscarPorCnpj(doc.getObjectId("cnpj").toString());
            Medico medico = medicoRepository.buscarPorNome(doc.getObjectId("nome").toString());

            if (hospital != null && medico != null) {
                return new HospitalMedico(doc.getObjectId("_id").toString(), hospital, medico);
            }
        }
        return null;
    }

    public void inserirRelacao(HospitalMedico relacao) {
        Document documento = new Document("hospitalId", new ObjectId(relacao.getHospital().getId()))
                .append("medicoId", new ObjectId(relacao.getMedico().getId()));
        colecao.insertOne(documento);
        System.out.println("Relação entre hospital e médico inserida com sucesso!");
    }

    public void excluirRelacao(String id) {
        colecao.deleteOne(eq("_id", new ObjectId(id)));
        System.out.println("Relação excluída com sucesso!");
    }
}
