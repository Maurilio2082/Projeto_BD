package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Tipo Sanguineos.
 * ##########################################################################
 */
public class TipoSanguineo {

    private int idTipoSanguineo;
    private String tipoSanguineo;
    private String fatorRh;

    public String getFatorRh() {
        return fatorRh;
    }

    public int getIdTipoSanguineo() {
        return idTipoSanguineo;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setFatorRh(String fatorRh) {
        this.fatorRh = fatorRh;
    }

    public void setIdTipoSanguineo(int idTipoSanguineo) {
        this.idTipoSanguineo = idTipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
}
