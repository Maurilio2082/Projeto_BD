package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
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

    public void atualizarRelacao(HospitalMedico relacaoAtualizada) {
        try {
            Bson filtro = eq("_id", new ObjectId(relacaoAtualizada.getId())); // Certifique-se de usar ObjectId

            // Criar documento de atualização
            Document atualizacao = new Document("$set", new Document()
                    .append("hospitalId", new ObjectId(relacaoAtualizada.getHospital().getId()))
                    .append("medicoId", new ObjectId(relacaoAtualizada.getMedico().getId())));

            // Atualizar no banco de dados
            colecao.updateOne(filtro, atualizacao);
            System.out.println("Relação médico-hospital atualizada com sucesso no banco de dados!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar a relação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<HospitalMedico> buscarTodasRelacoes() {
        List<HospitalMedico> relacoes = new ArrayList<>();
        MongoCursor<Document> cursor = colecao.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            // Recupera IDs de hospital e médico como String
            String hospitalId = doc.get("hospitalId").toString();
            String medicoId = doc.get("medicoId").toString();

            if (hospitalId != null && medicoId != null) {
                Hospital hospital = hospitalRepository.buscarPorId(hospitalId);
                Medico medico = medicoRepository.buscarPorId(medicoId);

                if (hospital != null && medico != null) {
                    relacoes.add(new HospitalMedico(doc.getObjectId("_id").toString(), hospital, medico));
                } else {
                    System.err.println("Erro ao buscar hospital ou médico para a relação: " + doc.getObjectId("_id"));
                }
            } else {
                System.err.println("Relação inválida encontrada na coleção: " + doc.toJson());
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
        try {
            Bson filtro = eq("_id", new ObjectId(id));
            colecao.deleteOne(filtro);
            System.out.println("Relação excluída com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir relação: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
