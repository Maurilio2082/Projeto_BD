package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Banco de Sangue.
 * ##########################################################################
 */
public class BancoSangue {

    private int idBancoSangue;
    private String quantidadeDisponivel;
    private TipoSanguineo idTipoSanguineo;
    private Hemocentro idHemocentro;

    public BancoSangue(int idBancoSangue, String quantidadeDisponivel) {
        this.idBancoSangue = idBancoSangue;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.idTipoSanguineo = null;
        this.idHemocentro = null;

    }

    public int getIdBancoSangue() {
        return idBancoSangue;
    }

    public Hemocentro getIdHemocentro() {
        return idHemocentro;
    }

    public TipoSanguineo getIdTipoSanguineo() {
        return idTipoSanguineo;
    }

    public String getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setIdBancoSangue(int idBancoSangue) {
        this.idBancoSangue = idBancoSangue;
    }

    public void setIdHemocentro(Hemocentro idHemocentro) {
        this.idHemocentro = idHemocentro;
    }

    public void setIdTipoSanguineo(TipoSanguineo idTipoSanguineo) {
        this.idTipoSanguineo = idTipoSanguineo;
    }

    public void setQuantidadeDisponivel(String quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

}
