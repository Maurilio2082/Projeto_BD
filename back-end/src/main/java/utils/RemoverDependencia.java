package utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import conexion.DatabaseConfig;

public class RemoverDependencia {

    public static void removerDependenciasHospital(String hospitalId) {
        MongoCollection<Document> hospitalMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("hospital_medico");
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");

        // Exclui relações em hospital_medico
        hospitalMedicoCollection.deleteMany(eq("hospitalId", hospitalId));
        // Exclui históricos relacionados ao hospital
        historicoCollection.deleteMany(eq("hospitalId", hospitalId));

        System.out.println("Dependências do hospital removidas com sucesso.");
    }

    public static void removerDependenciasMedico(String medicoId) {
        MongoCollection<Document> hospitalMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("hospital_medico");
        MongoCollection<Document> especialidadeMedicoCollection = DatabaseConfig.getDatabase()
                .getCollection("especialidade_medico");
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
                .getCollection("especialidade_medico");
        MongoCollection<Document> historicoCollection = DatabaseConfig.getDatabase().getCollection("historicos");

        // Exclui relações em especialidade_medico
        especialidadeMedicoCollection.deleteMany(eq("especialidadeId", especialidadeId));
        // Exclui históricos relacionados à especialidade
        historicoCollection.deleteMany(eq("especialidadeId", especialidadeId));

        System.out.println("Dependências da especialidade removidas com sucesso.");
    }
}
