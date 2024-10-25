package utils;

import java.sql.Connection;
import conexion.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class SplashScreen {

    private String qryTotalEspecialidades = "SELECT COUNT(1) AS TOTAL_ESPECIALIDADE FROM ESPECIALIDADE";
    private String qryTotalHospitais = "SELECT COUNT(1) AS TOTAL_HOSPITAIS FROM HOSPITAL";
    private String qryTotalPecientes = "SELECT COUNT(1) AS TOTAL_PACIETES FROM PACIENTE";
    private String qryTotalMedicos = "SELECT COUNT(1) AS TOTAL_MEDICOS FROM MEDICO";
    private String qryTotalHistoricos = "SELECT COUNT(1) AS TOTAL_HISTORICO FROM HISTORICO";

    private String createdBy = "Emmanuel, Jonathan, Maurilio ";
    private String professor = "Prof. M.Sc. Howard Roatti";
    private String disciplina = "Banco de Dados";
    private String semestre = "2024/2";

    public int getTotalEspecialidades() {
        return executarConsulta(qryTotalEspecialidades, "TOTAL_ESPECIALIDADE");
    }

    public int getQryTotalHospitais() {
        return executarConsulta(qryTotalHospitais, "TOTAL_HOSPITAIS");
    }

    public int getQryTotalPecientes() {
        return executarConsulta(qryTotalPecientes, "TOTAL_PACIETES");
    }

    public int getQryTotalMedicos() {
        return executarConsulta(qryTotalMedicos, "TOTAL_MEDICOS");
    }

    public int getQryTotalHistoricos() {
        return executarConsulta(qryTotalHistoricos, "TOTAL_HISTORICO");
    }

    private int executarConsulta(String consulta, String nomeColuna) {
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultado = stmt.executeQuery(consulta)) {

            if (resultado.next()) {
                return resultado.getInt(nomeColuna);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String obterTelaAtualizada() {
        return String.format("""
                ########################################################
                #                   SISTEMA DE PRONTUARIO
                #
                #  TOTAL DE REGISTROS:
                #      1 - ESPECIALIDADES:         %5d
                #      2 - HOSPITAIS:              %5d
                #      3 - PACIENTES:              %5d
                #      4 - MEDICOS:                %5d
                #      5 - HISTORICO               %5d
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
                getQryTotalHospitais(),
                getQryTotalPecientes(),
                getQryTotalMedicos(),
                getQryTotalHistoricos(),
                createdBy,
                professor,
                disciplina,
                semestre);
    }
}