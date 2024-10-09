package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Consulta.
 * ##########################################################################
 */

public class Consulta {

    private int idConsulta;
    private String dataConsulta;
    private int valorCredito;
    private Doador idDoador;
    private Especialidade idEspecialidade;
    private Hemocentro idHemocentro;

    public Consulta(int idConsulta, String dataConsulta, int valorCredito) {

        this.idConsulta = idConsulta;
        this.dataConsulta = dataConsulta;
        this.valorCredito = valorCredito;

    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public Doador getIdDoador() {
        return idDoador;
    }

    public Especialidade getIdEspecialidade() {
        return idEspecialidade;
    }

    public Hemocentro getIdHemocentro() {
        return idHemocentro;
    }

    public int getValorCredito() {
        return valorCredito;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setIdDoador(Doador idDoador) {
        this.idDoador = idDoador;
    }

    public void setIdEspecialidade(Especialidade idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public void setIdHemocentro(Hemocentro idHemocentro) {
        this.idHemocentro = idHemocentro;
    }

    public void setValorCredito(int valorCredito) {
        this.valorCredito = valorCredito;
    }

}
