package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Medico.
 * ##########################################################################
 */

public class Medico {
    private int idMedico;
    private String nomeMedico;
    private String conselho;

    public Medico(int idMedico, String nomeMedico, String conselho) {
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.conselho = conselho;

    }

    public int getIdMedico() {
        return idMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeEspecialidade) {
        this.nomeMedico = nomeEspecialidade;
    }

    public void setIdMedico(int idEspecialidade) {
        this.idMedico = idEspecialidade;
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }
}
