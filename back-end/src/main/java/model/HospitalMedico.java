package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospital_medico") // Nome da coleção no MongoDB
public class HospitalMedico {

    @Id
    private String id; // Identificador único no MongoDB
    private String hospitalId; // Referência ao ID do hospital no MongoDB
    private String medicoId; // Referência ao ID do médico no MongoDB

    public HospitalMedico() {
    }

    public HospitalMedico(String id, String hospitalId, String medicoId) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.medicoId = medicoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "HospitalMedico [id=" + id + ", hospitalId=" + hospitalId + ", medicoId=" + medicoId + "]";
    }
}
