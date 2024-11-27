package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historicos") // Nome da coleção no MongoDB
public class Historico {

    @Id
    private String id; // Identificador único no MongoDB
    private String dataConsulta;
    private String observacao;
    private String pacienteId; // Referência ao ID do paciente no MongoDB
    private String hospitalId; // Referência ao ID do hospital no MongoDB
    private String medicoId; // Referência ao ID do médico no MongoDB
    private String especialidadeId; // Referência ao ID da especialidade no MongoDB

    public Historico() {
    }

    public Historico(String id, String dataConsulta, String observacao, String pacienteId, String hospitalId,
            String medicoId, String especialidadeId) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.observacao = observacao;
        this.pacienteId = pacienteId;
        this.hospitalId = hospitalId;
        this.medicoId = medicoId;
        this.especialidadeId = especialidadeId;
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

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    public String getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(String especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    @Override
    public String toString() {
        return "Historico [id=" + id + ", dataConsulta=" + dataConsulta + ", observacao=" + observacao +
                ", pacienteId=" + pacienteId + ", hospitalId=" + hospitalId +
                ", medicoId=" + medicoId + ", especialidadeId=" + especialidadeId + "]";
    }
}
