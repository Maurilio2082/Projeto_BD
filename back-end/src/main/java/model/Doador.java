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
    private int quantidadeDoada;
    private Endereco id_endereco;

    public Doador(int idDoador, String nome, String cpf, String email, String telefone, String tipoSanguineo,
            String dataNascimento) {

        this.idDoador = idDoador;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.tipoSanguineo = tipoSanguineo;
        this.dataNascimento = dataNascimento;
        this.quantidadeDoada = 0;

    }

    public Endereco getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(Endereco id_endereco) {
        this.id_endereco = id_endereco;
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

    public int getQuantidadeDoada() {
        return quantidadeDoada;
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

    public void setQuantidadeDoada(int quantidadeDoada) {
        this.quantidadeDoada = quantidadeDoada;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {

        return super.toString();
    }
}
