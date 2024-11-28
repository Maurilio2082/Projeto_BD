package reports;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import conexion.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Relatorios {

    private String queryRelatorioEspecialidades;
    private String queryRelatorioHospitais;
    private String queryRelatorioPacientes;
    private String queryRelatorioMedicos;
    private String queryRelatorioHistoricos;
    private String queryRelatorioEspecialidadeMedicos;
    private String queryRelatorioHospitaisMedicos;
    private String queryRelatorioAgrupamentoEsp;

    private final MongoCollection<Document> historicosCollection;
    private final MongoCollection<Document> pacientesCollection;
    private final MongoCollection<Document> medicosCollection;
    private final MongoCollection<Document> hospitaisCollection;
    private final MongoCollection<Document> especialidadesCollection;

    public Relatorios() {
        this.historicosCollection = DatabaseConfig.getDatabase().getCollection("historicos");
        this.pacientesCollection = DatabaseConfig.getDatabase().getCollection("pacientes");
        this.medicosCollection = DatabaseConfig.getDatabase().getCollection("medicos");
        this.hospitaisCollection = DatabaseConfig.getDatabase().getCollection("hospitais");
        this.especialidadesCollection = DatabaseConfig.getDatabase().getCollection("especialidades");

        queryRelatorioEspecialidades = lerArquivoConsulta("mongo_queries/listar_especialidades.json");
        queryRelatorioHospitais = lerArquivoConsulta("mongo_queries/listar_hospitais.json");
        queryRelatorioPacientes = lerArquivoConsulta("mongo_queries/listar_pacientes.json");
        queryRelatorioMedicos = lerArquivoConsulta("mongo_queries/listar_medicos.json");
        queryRelatorioHistoricos = lerArquivoConsulta("mongo_queries/listar_historicos.json");
        queryRelatorioEspecialidadeMedicos = lerArquivoConsulta("mongo_queries/listar_especialidade_medicos.json");
        queryRelatorioHospitaisMedicos = lerArquivoConsulta("mongo_queries/listar_hospitais_medicos.json");
        queryRelatorioAgrupamentoEsp = lerArquivoConsulta("mongo_queries/listar_agrupamento_especialidade.json");
    }

    private String lerArquivoConsulta(String caminhoArquivo) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(caminhoArquivo)) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
                }
            } else {
                System.err.println("Arquivo de consulta não encontrado: " + caminhoArquivo);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de consulta: " + caminhoArquivo);
            e.printStackTrace();
            return null;
        }
    }

    // Relatório 1: Listar Especialidades
    public List<Document> getRelatorioEspecialidades() {
        return executarConsulta(especialidadesCollection, queryRelatorioEspecialidades);
    }

    // Relatório 2: Listar Hospitais
    public List<Document> getRelatorioHospitais() {
        return executarConsulta(hospitaisCollection, queryRelatorioHospitais);
    }

    // Relatório 3: Listar Pacientes
    public List<Document> getRelatorioPacientes() {
        return executarConsulta(pacientesCollection, queryRelatorioPacientes);
    }

    // Relatório 4: Listar Médicos
    public List<Document> getRelatorioMedicos() {
        return executarConsulta(medicosCollection, queryRelatorioMedicos);
    }

    // Relatório 5: Listar Históricos
    public List<Document> getRelatorioHistoricos() {
        return executarConsulta(historicosCollection, queryRelatorioHistoricos);
    }

    // Relatório 6: Médicos por Especialidade

    public List<Document> getRelatorioEspecialidadeMedicos() {
        MongoCollection<Document> especialidadesMedicosCollection = DatabaseConfig.getDatabase()
                .getCollection("especialidades_medicos");
        return executarConsulta(especialidadesMedicosCollection, queryRelatorioEspecialidadeMedicos);
    }

    // Relatório 7: Médicos por Hospital
    public List<Document> getRelatorioHospitaisMedicos() {
        return executarConsulta(historicosCollection, queryRelatorioHospitaisMedicos);
    }

    // Relatório 8: Agrupamento de Especialidades
    public List<Document> getRelatorioAgrupamentoEsp() {
        return executarConsulta(medicosCollection, queryRelatorioAgrupamentoEsp);
    }

    private List<Document> executarConsulta(MongoCollection<Document> collection, String queryJson) {
        List<Document> resultados = new ArrayList<>();
        if (queryJson == null) {
            System.err.println("A consulta JSON está nula.");
            return resultados;
        }

        try {
            // Converta o JSON da consulta em uma lista de estágios de pipeline
            List<Document> pipeline = parsePipeline(queryJson);

            // Execute a consulta de agregação
            MongoCursor<Document> cursor = collection.aggregate(pipeline).iterator();

            while (cursor.hasNext()) {
                resultados.add(cursor.next());
            }
            cursor.close();
        } catch (Exception e) {
            System.err.println("Erro ao executar consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    private List<Document> parsePipeline(String queryJson) {
        try {
            // Use a biblioteca de JSON para interpretar a string como uma lista de
            // documentos
            return Document.parse("{\"pipeline\": " + queryJson + "}").getList("pipeline", Document.class);
        } catch (Exception e) {
            System.err.println("Erro ao interpretar o pipeline JSON: " + e.getMessage());
            throw e;
        }
    }

}
