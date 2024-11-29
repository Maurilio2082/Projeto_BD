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
import model.EspecialidadeMedico;
import model.Medico;

import static com.mongodb.client.model.Filters.eq;

public class EspecialidadeMedicoRepository {

    private final MongoCollection<Document> colecao;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeMedicoRepository() {
        this.colecao = DatabaseConfig.getDatabase().getCollection("especialidades_medicos");
        this.medicoRepository = new MedicoRepository(); // Inicializando o repositório de médicos
        this.especialidadeRepository = new EspecialidadeRepository(); // Inicializando o repositório de especialidades
    }

    public List<EspecialidadeMedico> buscarTodasRelacoes() {
        List<EspecialidadeMedico> relacoes = new ArrayList<>();
        MongoCursor<Document> cursor = colecao.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            // Obter IDs de médico e especialidade do documento
            ObjectId medicoId = doc.getObjectId("medicoId");
            ObjectId especialidadeId = doc.getObjectId("especialidadeId");

            // Verificar se ambos IDs existem
            if (medicoId == null) {
                System.err.println("Relação inválida: ID do médico está ausente.");
                continue;
            }

            if (especialidadeId == null) {
                System.err.println("Relação inválida: ID da especialidade está ausente.");
                continue;
            }

            // Buscar médico e especialidade pelo ID
            Medico medico = medicoRepository.buscarPorId(medicoId.toString());
            Especialidade especialidade = especialidadeRepository.buscarPorId(especialidadeId.toString());

            // Adicionar à lista se ambos forem encontrados
            if (medico != null && especialidade != null) {
                relacoes.add(new EspecialidadeMedico(doc.getObjectId("_id").toString(), medico, especialidade));
            } else {
                System.err.println("Erro ao buscar médico ou especialidade para a relação: " + doc.getObjectId("_id"));
            }
        }
        cursor.close();
        return relacoes;
    }

    public void atualizarRelacao(EspecialidadeMedico relacaoAtualizada) {
        try {
            Bson filtro = eq("_id", new ObjectId(relacaoAtualizada.getId())); // Certifique-se de usar ObjectId

            // Criar documento de atualização
            Document atualizacao = new Document("$set", new Document()
                    .append("medicoId", new ObjectId(relacaoAtualizada.getMedico().getId()))
                    .append("especialidadeId", new ObjectId(relacaoAtualizada.getEspecialidade().getId())));

            // Atualizar no banco de dados
            colecao.updateOne(filtro, atualizacao);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar a relação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public EspecialidadeMedico buscarPorMedicoId(String medicoId) {
        Bson filtro = eq("medicoId", new ObjectId(medicoId));
        Document doc = colecao.find(filtro).first();

        if (doc != null) {
            Medico medico = medicoRepository.buscarPorNome(doc.getObjectId("nome").toString());
            Especialidade especialidade = especialidadeRepository
                    .buscarPorId(doc.getObjectId("especialidadeId").toString());

            if (medico != null && especialidade != null) {
                return new EspecialidadeMedico(doc.getObjectId("_id").toString(), medico, especialidade);
            }
        }
        return null;
    }

    public void inserirRelacao(EspecialidadeMedico relacao) {
        Document documento = new Document("medicoId", new ObjectId(relacao.getMedico().getId()))
                .append("especialidadeId", new ObjectId(relacao.getEspecialidade().getId()));
        colecao.insertOne(documento);
    }

    public void excluirRelacao(String id) {
        try {
            Bson filtro = eq("_id", new ObjectId(id)); // Certifique-se de usar ObjectId
            colecao.deleteOne(filtro);
        } catch (Exception e) {
            System.err.println("Erro ao excluir relação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Especialidade> buscarEspecialidadesPorMedico(String medicoId) {
        MongoCollection<Document> colecaoEspecialidadeMedico = DatabaseConfig.getDatabase()
                .getCollection("especialidades_medicos");

        List<Especialidade> especialidades = new ArrayList<>();
        Bson filtro = eq("medicoId", new ObjectId(medicoId));
        MongoCursor<Document> cursor = colecaoEspecialidadeMedico.find(filtro).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String especialidadeId = doc.getObjectId("especialidadeId").toString();

            // Busca a especialidade pelo ID
            Especialidade especialidade = especialidadeRepository.buscarEspecialidadePorId(especialidadeId);
            if (especialidade != null) {
                especialidades.add(especialidade);
            }
        }
        cursor.close();
        return especialidades;
    }

}
