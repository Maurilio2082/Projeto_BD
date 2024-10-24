package model;

public class HospitalMedico {

    Hospital idHospital;
    Medico idMedico;

    public HospitalMedico(Hospital idHospital, Medico idMedico) {
        this.idHospital = idHospital;
        this.idMedico = idMedico;
    }

    public Hospital getIdHospital() {
        return idHospital;
    }

    public Medico getIdMedico() {
        return idMedico;
    }

    public void setIdHospital(Hospital idHospital) {
        this.idHospital = idHospital;
    }

    public void setIdMedico(Medico idMedico) {
        this.idMedico = idMedico;
    }

}
