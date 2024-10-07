package model;



/*
 * ##########################################################################
 * # Classe basica do objeto Hemocentro.
 * ##########################################################################
 */
public class Hemocentro {

    private int idHemocentro;
    private String razaoSocial;
    private String email;
    private String telefone;
    private String cnpj;
    private Endereco endereco;

    public Hemocentro(int idHemocentro, String razaoSocial, String cnpj, String email, String telefone,
            Endereco endereco) {
        this.idHemocentro = idHemocentro;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getIdHemocentro() {
        return idHemocentro;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setIdHemocentro(int idHemocentro) {
        this.idHemocentro = idHemocentro;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
