package model;

public class Especialidade {
    private int idEspecialidade;
    private String nomeEspecialidade;

    public Especialidade(int idEspecialidade, String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
        this.idEspecialidade = idEspecialidade;
    }

    public int getIdEspecialidade() {
        return idEspecialidade;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

}
