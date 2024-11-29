package utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import conexion.DatabaseConfig;

public class RemoverDependencia {

    public static void removerDependenciasMedico(String medicoId) {
        MongoCollection<Document> hospitalMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("hospitais_medicos");
        MongoCollection<Document> especialidadeMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("especialidades_medicos");
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");

        // Exclui relações em hospital_medico
        hospitalMedicoCollection.deleteMany(eq("medicoId", medicoId));
        // Exclui relações em especialidade_medico
        especialidadeMedicoCollection.deleteMany(eq("medicoId", medicoId));
        // Exclui históricos relacionados ao médico
        historicoCollection.deleteMany(eq("medicoId", medicoId));

        System.out.println("Dependências do médico removidas com sucesso.");
    }

    public static void removerDependenciasPaciente(String pacienteId) {
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");

        // Exclui históricos relacionados ao paciente
        historicoCollection.deleteMany(eq("pacienteId", pacienteId));

        System.out.println("Dependências do paciente removidas com sucesso.");
    }

    public static void removerDependenciasEspecialidade(String especialidadeId) {
        MongoCollection<Document> especialidadeMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("especialidades_medicos");
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");

        try {
            // Exclui relações em especialidade_medico
            long removidosEspecialidadeMedico = especialidadeMedicoCollection.deleteMany(
                    eq("especialidadeId", new ObjectId(especialidadeId))).getDeletedCount();
            System.out.println("Relações removidas em especialidade_medico: " + removidosEspecialidadeMedico);

            // Exclui históricos relacionados à especialidade
            long removidosHistorico = historicoCollection.deleteMany(
                    eq("especialidadeId", new ObjectId(especialidadeId))).getDeletedCount();
            System.out.println("Relações removidas em historicos: " + removidosHistorico);

            System.out.println("Dependências da especialidade removidas com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao remover dependências da especialidade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void removerDependenciasHospital(String hospitalId) {
        MongoCollection<Document> hospitalMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("hospitais_medicos");
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");
    
        // Verifica e exclui relações em hospital_medico
        System.out.println("Removendo relações em hospitais_medicos...");
        long removidosHospitalMedico = hospitalMedicoCollection.deleteMany(eq("hospitalId", new ObjectId(hospitalId))).getDeletedCount();
        System.out.println(removidosHospitalMedico + " relações em hospitais_medicos removidas.");
    
        // Verifica e exclui históricos relacionados ao hospital
        System.out.println("Removendo históricos relacionados ao hospital...");
        long removidosHistorico = historicoCollection.deleteMany(eq("hospitalId", new ObjectId(hospitalId))).getDeletedCount();
        System.out.println(removidosHistorico + " históricos removidos.");
    
        System.out.println("Dependências do hospital removidas com sucesso.");
    }
    

}
