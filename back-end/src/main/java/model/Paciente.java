package model;

/*
 * ##########################################################################
 * # Classe basica do objeto Paciente.
 * ##########################################################################
 */
public class Paciente {

    private int idPaciente;
    private String nomePaciente;
    private String email;
    private String telefone;
    private String dataNascimento;
    private String cpf;
    private Endereco idEndereco;

    public Paciente(int idPaciente, String nomePaciente, String dataNascimento, String email, String telefone,
            String cpf,
            Endereco idEndereco) {
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.idEndereco = idEndereco;
    }

    public Endereco getIdEndereco() {
        return idEndereco;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
