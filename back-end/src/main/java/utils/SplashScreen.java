package utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.DatabaseConfig;
import org.bson.Document;

public class SplashScreen {

    private final MongoDatabase database;

    private final String createdBy = "Emmanuel, Jonathan, Maurilio";
    private final String professor = "Prof. M.Sc. Howard Roatti";
    private final String disciplina = "Banco de Dados";
    private final String semestre = "2024/2";

    public SplashScreen() {
        this.database = DatabaseConfig.getDatabase();
    }

    public long getTotalEspecialidades() {
        return contarRegistros("especialidades");
    }

    public long getTotalHospitais() {
        return contarRegistros("hospitais");
    }

    public long getTotalPacientes() {
        return contarRegistros("pacientes");
    }

    public long getTotalMedicos() {
        return contarRegistros("medicos");
    }

    public long getTotalHistoricos() {
        return contarRegistros("historicos");
    }

    private long contarRegistros(String collectionName) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String obterTelaAtualizada() {
        return String.format("""
                ########################################################
                #                   SISTEMA DE PRONTUÁRIO
                #
                #  TOTAL DE REGISTROS:
                #      1 - ESPECIALIDADES:         %5d
                #      2 - HOSPITAIS:              %5d
                #      3 - PACIENTES:              %5d
                #      4 - MÉDICOS:                %5d
                #      5 - HISTÓRICO               %5d
                #
                #  CRIADO POR: %s
                #
                #  PROFESSOR:  %s
                #
                #  DISCIPLINA: %s
                #              %s
                ########################################################
                """,
                getTotalEspecialidades(),
                getTotalHospitais(),
                getTotalPacientes(),
                getTotalMedicos(),
                getTotalHistoricos(),
                createdBy,
                professor,
                disciplina,
                semestre);
    }
}
