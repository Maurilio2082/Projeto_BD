package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Historico.
 * ##########################################################################
 */

public class Historico {

    private int idHistorico;
    private String dataConsulta;
    private String observacao;
    private Paciente idPaciente;
    private Hospital idHospital;

    public Historico(int idHistorico, String dataConsulta, String observacao,
            Paciente idPaciente, Hospital idHospital) {

        this.idHistorico = idHistorico;
        this.dataConsulta = dataConsulta;
        this.observacao = observacao;
        this.idPaciente = idPaciente;
        this.idHospital = idHospital;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHospital(Hospital idHospital) {
        this.idHospital = idHospital;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Hospital getIdHospital() {
        return idHospital;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return String.format("%-8d %-20s %-15s %-28s %-15s %-18s %-8s",
                idHistorico, dataConsulta, observacao,
                idHospital != null ? idHospital.getRazaoSocial() : "N/A",
                idHospital != null ? idHospital.getCnpj() : "N/A",
                idPaciente != null ? idPaciente.getNomePaciente() : "N/A",
                idPaciente != null ? idPaciente.getCpf() : "N/A");
    }
    

}