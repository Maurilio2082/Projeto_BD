package model;

public class EspecidalidadeMedico {

    private Especialidade idEspecialidade;
    private Medico idMedico;

    public EspecidalidadeMedico(Especialidade idEspecialidade, Medico idMedico) {
        this.idEspecialidade = idEspecialidade;
        this.idMedico = idMedico;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public Medico getIdMedico() {
        return idMedico;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public void setIdMedico(Medico idMedico) {
        this.idMedico = idMedico;
    }

}
