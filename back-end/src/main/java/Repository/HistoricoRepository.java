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

                PacienteRepository pacienteRepository = new PacienteRepository();
                HospitalRepository hospitalRepository = new HospitalRepository();
                MedicoRepository medicoRepository = new MedicoRepository();
                EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

                while (cursor.hasNext()) {
                        Document doc = cursor.next();

                        // Buscar paciente
                        Paciente paciente = pacienteRepository.buscarPorId(doc.getObjectId("pacienteId").toString());

                        // Buscar hospital
                        Hospital hospital = hospitalRepository.buscarPorId(doc.getObjectId("hospitalId").toString());

                        // Buscar médico
                        Medico medico = medicoRepository.buscarPorId(doc.getObjectId("medicoId").toString());

                        // Buscar especialidade
                        Especialidade especialidade = especialidadeRepository
                                        .buscarPorId(doc.getObjectId("especialidadeId").toString());

                        if (paciente != null && hospital != null && medico != null && especialidade != null) {
                                historicos.add(new Historico(
                                                doc.getObjectId("_id").toString(),
                                                doc.getString("dataConsulta"),
                                                doc.getString("observacao"),
                                                paciente,
                                                hospital,
                                                medico,
                                                especialidade));
                        } else {
                                System.err.println("Erro ao buscar dados relacionados para o histórico ID: "
                                                + doc.getObjectId("_id"));
                        }
                }
                cursor.close();
                return historicos;
        }

        public Historico buscarPorId(String id) {
                Bson filtro = eq("_id", new ObjectId(id));
                Document doc = colecao.find(filtro).first();

                if (doc != null) {
                        PacienteRepository pacienteRepository = new PacienteRepository();
                        HospitalRepository hospitalRepository = new HospitalRepository();
                        MedicoRepository medicoRepository = new MedicoRepository();
                        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

                        // Buscar paciente
                        Paciente paciente = pacienteRepository.buscarPorId(doc.getObjectId("pacienteId").toString());

                        // Buscar hospital
                        Hospital hospital = hospitalRepository.buscarPorId(doc.getObjectId("hospitalId").toString());

                        // Buscar médico
                        Medico medico = medicoRepository.buscarPorId(doc.getObjectId("medicoId").toString());

                        // Buscar especialidade
                        Especialidade especialidade = especialidadeRepository
                                        .buscarPorId(doc.getObjectId("especialidadeId").toString());

                        if (paciente != null && hospital != null && medico != null && especialidade != null) {
                                return new Historico(
                                                doc.getObjectId("_id").toString(),
                                                doc.getString("dataConsulta"),
                                                doc.getString("observacao"),
                                                paciente,
                                                hospital,
                                                medico,
                                                especialidade);
                        } else {
                                System.err.println("Erro ao buscar dados relacionados para o histórico ID: "
                                                + doc.getObjectId("_id"));
                        }
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
                try {
                        Bson filtro = eq("_id", new ObjectId(id));
                        colecao.deleteOne(filtro);
                        System.out.println("Histórico excluído com sucesso!");
                } catch (Exception e) {
                        System.err.println("Erro ao excluir histórico: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        public void atualizarHistorico(Historico historicoAtualizado) {
                try {
                        Bson filtro = eq("_id", new ObjectId(historicoAtualizado.getId()));

                        // Criar o documento de atualização
                        Document atualizacao = new Document("$set", new Document()
                                        .append("dataConsulta", historicoAtualizado.getDataConsulta())
                                        .append("observacao", historicoAtualizado.getObservacao())
                                        .append("pacienteId", new ObjectId(historicoAtualizado.getPaciente().getId()))
                                        .append("hospitalId", new ObjectId(historicoAtualizado.getHospital().getId()))
                                        .append("medicoId", new ObjectId(historicoAtualizado.getMedico().getId()))
                                        .append("especialidadeId",
                                                        new ObjectId(historicoAtualizado.getEspecialidade().getId())));

                        // Atualizar no banco de dados
                        colecao.updateOne(filtro, atualizacao);
                        System.out.println("Histórico atualizado com sucesso no banco de dados!");
                } catch (Exception e) {
                        System.err.println("Erro ao atualizar o histórico: " + e.getMessage());
                        e.printStackTrace();
                }
        }

}
