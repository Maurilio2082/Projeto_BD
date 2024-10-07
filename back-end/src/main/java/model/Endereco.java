package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Endereco.
 * ##########################################################################
 */

public class Endereco {

    private int id_endereco;
    private String longradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;

    public Endereco(int id_endereco, String longradouro, String numero, String complemento, String bairro,
            String cidade,
            String estado,
            String cep) {
        this.id_endereco = id_endereco;
        this.longradouro = longradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.latitude = ""; // Nao vou usar nesse sistema, usar no projeto frontend
        this.longitude = ""; // Nao vou usar nesse sistema, usar no projeto frontend
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public String getLongradouro() {
        return longradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setLongradouro(String longradouro) {
        this.longradouro = longradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {

        return super.toString();
    }

}
