package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitais_medicos")
public class HospitalMedico {

    @Id
    private String id; 
    private Hospital hospital; 
    private Medico medico; 

    public HospitalMedico() {
    }

    public HospitalMedico(String id, Hospital hospital, Medico medico) {
        this.id = id;
        this.hospital = hospital;
        this.medico = medico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "HospitalMedico [id=" + id + ", hospital=" + hospital + ", medico=" + medico + "]";
    }
}
