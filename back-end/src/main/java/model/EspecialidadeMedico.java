package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especialidades_medicos")
public class EspecialidadeMedico {

    @Id
    private String id; // ID da relação no banco, se necessário
    private Medico medico;
    private Especialidade especialidade;

    public EspecialidadeMedico(String id, Medico medico, Especialidade especialidade) {
        this.id = id;
        this.medico = medico;
        this.especialidade = especialidade;
    }
    

    public EspecialidadeMedico(Medico medico, Especialidade especialidade) {
        this.medico = medico;
        this.especialidade = especialidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "EspecialidadeMedico{" +
                "id='" + id + '\'' +
                ", medico=" + medico +
                ", especialidade=" + especialidade +
                '}';
    }
}
