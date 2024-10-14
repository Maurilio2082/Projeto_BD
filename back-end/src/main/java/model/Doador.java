package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Doador.
 * ##########################################################################
 */

public class Doador {

    private int idDoador;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String tipoSanguineo;
    private String dataNascimento;
    private String quantidadeDoacoes;
    private String peso;
    private Endereco idEndereco;
    private TipoSanguineo idTipoSanguineo;

    public Doador(int idDoador, String nome, String cpf, String email, String telefone,
            String dataNascimento, String peso, String quantidadeDoacoes,
            Endereco idEndereco, TipoSanguineo idTipoSanguineo) {

        this.idDoador = idDoador;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.quantidadeDoacoes = quantidadeDoacoes;
        this.idEndereco = idEndereco;
        this.idTipoSanguineo = idTipoSanguineo;

    }

    public Endereco getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Endereco id_endereco) {
        this.idEndereco = id_endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public int getIdDoador() {
        return idDoador;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdDoador(int idDoador) {
        this.idDoador = idDoador;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setQuantidadeDoacoes(String quantidadeDoacoes) {
        this.quantidadeDoacoes = quantidadeDoacoes;
    }

    public String getPeso() {
        return peso;
    }

    public String getQuantidadeDoacoes() {
        return quantidadeDoacoes;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoSanguineo getIdTipoSanguineo() {
        return idTipoSanguineo;
    }

    public void setIdTipoSanguineo(TipoSanguineo idTipoSanguineo) {
        this.idTipoSanguineo = idTipoSanguineo;
    }

    @Override
public String toString() {
    return String.format("%-8d %-20s %-15s %-28s %-15s %-18s %-8s %-15s %-28s %-15s %-15s %-15s",
            idDoador, nome, cpf, email, telefone, dataNascimento, peso, quantidadeDoacoes,
            idEndereco != null ? idEndereco.getLogradouro() : "N/A",
            idEndereco != null ? idEndereco.getBairro() : "N/A",
            idEndereco != null ? idEndereco.getCidade() : "N/A",
            idEndereco != null ? idEndereco.getEstado() : "N/A",
            idTipoSanguineo != null ? idTipoSanguineo.getTipoSanguineo() : "N/A",
            idTipoSanguineo != null ? idTipoSanguineo.getFatorRh() : "N/A");
}

}