package utils;

import java.sql.Connection;
import conexion.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class SplashScreen {

    private String qryTotalEspecialidade = "SELECT COUNT(1) AS total_especialidades FROM especialidades";
    private String qryTotaAgendamentos = "SELECT COUNT(1) AS total_agendamentos FROM agendamentos";
    private String qryTotalBancoSangue = "SELECT COUNT(1) AS total_banco_sangue FROM banco_sangue";
    private String qryTotalDoador = "SELECT COUNT(1) AS total_doador FROM doador";
    private String qryTotalPrestador = "SELECT COUNT(1) AS total_prestador FROM prestador";

    private String createdBy = "Emmanuel Barcelos, Jonathan Prado , Maurilio Marques";
    private String professor = "Prof. M.Sc. Howard Roatti";
    private String disciplina = "Banco de Dados";
    private String semestre = "2024/2";

    public int getTotalEspecialidade() {
        return executarConsulta(qryTotalEspecialidade, "total_especialidades");
    }

    public int getQryTotalAgendamentos() {
        return executarConsulta(qryTotaAgendamentos, "total_agendamentos");
    }

    public int getQryTotalBancoSangue() {
        return executarConsulta(qryTotalBancoSangue, "total_banco_sangue");
    }

    public int getQryTotalDoador() {
        return executarConsulta(qryTotalDoador, "total_doador");
    }

    public int getQryTotalPrestador() {
        return executarConsulta(qryTotalPrestador, "total_prestador");
    }

    private int executarConsulta(String consulta, String nomeColuna) {
        try (Connection conn = DatabaseConfig.getConnection(); // Obtém a conexão com o banco de dados
                Statement stmt = conn.createStatement(); // Cria a declaração SQL
                ResultSet resultado = stmt.executeQuery(consulta)) { // Executa a consulta SQL

            // Se houver resultado, retorna o valor da coluna especificada
            if (resultado.next()) {
                return resultado.getInt(nomeColuna);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Exibe o erro, caso ocorra
        }
        return 0; // Retorna 0 se nenhum resultado for encontrado ou ocorrer erro
    }

    public String obterTelaAtualizada() {
        return String.format("""
                ########################################################
                #                   SISTEMA DE DOAÇÕES
                #
                #  TOTAL DE REGISTROS:
                #      1 - ESPECIALIDADES:         %5d
                #      2 - AGENDAMENTOS:           %5d
                #      3 - DOADORES:               %5d
                #      4 - PRESTADORES:            %5d
                #
                #  CRIADO POR: %s
                #
                #  PROFESSOR:  %s
                #
                #  DISCIPLINA: %s
                #              %s
                ########################################################
                """,
                getTotalEspecialidade(),
                getQryTotalAgendamentos(),
                getQryTotalDoador(),
                getQryTotalPrestador(),
                createdBy,
                professor,
                disciplina,
                semestre);
    }
}
