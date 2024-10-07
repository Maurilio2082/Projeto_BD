package model;


/*
 * ##########################################################################
 * # Classe basica do objeto Consulta.
 * ##########################################################################
 */

public class Consulta {

    private int idConsulta;
    private String dataConsulta;
    private String valorCredito;
    private Doador iDoador;
    private Especialidade iEspecialidade;
    private Hemocentro iHemocentro;

    public String getDataConsulta() {
        return dataConsulta;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public String getValorCredito() {
        return valorCredito;
    }

    public Doador getiDoador() {
        return iDoador;
    }

    public Especialidade getiEspecialidade() {
        return iEspecialidade;
    }

    public Hemocentro getiHemocentro() {
        return iHemocentro;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setValorCredito(String valorCredito) {
        this.valorCredito = valorCredito;
    }

    public void setiDoador(Doador iDoador) {
        this.iDoador = iDoador;
    }

    public void setiEspecialidade(Especialidade iEspecialidade) {
        this.iEspecialidade = iEspecialidade;
    }

    public void setiHemocentro(Hemocentro iHemocentro) {
        this.iHemocentro = iHemocentro;
    }

}
