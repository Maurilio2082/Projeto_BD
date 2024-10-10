package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Endereco.
 * ##########################################################################
 */

public class Endereco {

    private int idEndereco;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;

    public Endereco(int idEndereco, String logradouro, String numero, String complemento, String bairro,
            String cidade,
            String estado,
            String cep) {
        this.idEndereco = idEndereco;
        this.logradouro = logradouro;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
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

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

    public void setId_endereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override

    public String toString() {
        return String.format("%-10d %-40s %-10s %-20s %-20s %-15s %-10s %-10s",
                idEndereco, logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

}
