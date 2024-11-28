package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historicos") // Nome da coleção no MongoDB
public class Historico {

    @Id
    private String id; // Identificador único no MongoDB
    private String dataConsulta;
    private String observacao;
    private Paciente paciente; // Associação ao paciente
    private Hospital hospital; // Associação ao hospital
    private Medico medico; // Associação ao médico
    private Especialidade especialidade; // Associação à especialidade

    public Historico() {
    }

    public Historico(String id, String dataConsulta, String observacao, Paciente paciente, Hospital hospital,
            Medico medico, Especialidade especialidade) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.observacao = observacao;
        this.paciente = paciente;
        this.hospital = hospital;
        this.medico = medico;
        this.especialidade = especialidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Historico [id=" + id + ", dataConsulta=" + dataConsulta + ", observacao=" + observacao +
                ", pacienteId=" + paciente + ", hospitalId=" + hospital +
                ", medicoId=" + medico + ", especialidadeId=" + especialidade + "]";
    }
}
