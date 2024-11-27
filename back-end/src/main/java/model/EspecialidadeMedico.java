package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especialidade_medico") // Nome da coleção no MongoDB
public class EspecialidadeMedico {

    @Id
    private String id; // Identificador único no MongoDB
    private String medicoId; // Referência ao ID do médico no MongoDB
    private String especialidadeId; // Referência ao ID da especialidade no MongoDB

    public EspecialidadeMedico() {
    }

    public EspecialidadeMedico(String id, String medicoId, String especialidadeId) {
        this.id = id;
        this.medicoId = medicoId;
        this.especialidadeId = especialidadeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "EspecialidadeMedico [id=" + id + ", medicoId=" + medicoId + ", especialidadeId=" + especialidadeId + "]";
    }
}
