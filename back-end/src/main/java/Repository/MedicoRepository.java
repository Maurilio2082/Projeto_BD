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
        try {
            // Garantir que o ID seja tratado como ObjectId
            Bson filtro = eq("_id", new ObjectId(id));
    
            // Construir o documento de atualização
            Document atualizacao = new Document("$set", new Document()
                    .append("nome", medicoAtualizado.getNome())
                    .append("conselho", medicoAtualizado.getConselho()));
    
            // Atualizar no banco de dados
            colecao.updateOne(filtro, atualizacao);
            System.out.println("Médico atualizado com sucesso no banco de dados!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar médico no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void excluirMedico(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Garantir que o ID seja tratado como ObjectId
            colecao.deleteOne(filtro);
            System.out.println("Médico excluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir médico: " + e.getMessage());
            e.printStackTrace();
        }
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

    public void removerDependenciasMedico(String medicoId) {
        System.out.println("Removendo dependências do médico com ID: " + medicoId);

        try {
            // Remover associações do médico com hospitais
            MongoCollection<Document> colecaoHospitalMedico = DatabaseConfig.getDatabase()
                    .getCollection("hospitais_medicos");
            Bson filtroHospitalMedico = eq("medicoId", new ObjectId(medicoId));
            colecaoHospitalMedico.deleteMany(filtroHospitalMedico);
            System.out.println("Associações do médico com hospitais removidas.");

            // Remover associações do médico com especialidades
            MongoCollection<Document> colecaoEspecialidadeMedico = DatabaseConfig.getDatabase()
                    .getCollection("especialidades_medicos");
            Bson filtroEspecialidadeMedico = eq("medicoId", new ObjectId(medicoId));
            colecaoEspecialidadeMedico.deleteMany(filtroEspecialidadeMedico);
            System.out.println("Associações do médico com especialidades removidas.");

        } catch (Exception e) {
            System.err.println("Erro ao remover dependências do médico: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
