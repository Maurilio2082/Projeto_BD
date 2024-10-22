package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Hospital
 * .
 * ##########################################################################
 */
public class Hospital {

    private int idHospital;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String categoria;
    private Endereco idEndereco;

    public Hospital(int idHospital, String razaoSocial, String cnpj, String email, String telefone,
            String categoria,
            Endereco idEndereco) {
        this.idHospital = idHospital;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.categoria = categoria;
        this.email = email;
        this.telefone = telefone;
        this.idEndereco = idEndereco;
    }

    public Endereco getIdEndereco() {
        return idEndereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String dataNascimento) {
        this.categoria = dataNascimento;
    }

    public void setIdEndereco(Endereco id_endereco) {
        this.idEndereco = id_endereco;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cpf) {
        this.cnpj = cpf;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-30s %-15s %-30s %-20s %-30s %-20s %-15s %-15s %-15s",
                idHospital, razaoSocial, cnpj, email, telefone,
                idEndereco.getLogradouro(), idEndereco.getNumero(),
                idEndereco.getBairro(), idEndereco.getCidade(), idEndereco.getEstado());
    }
}
