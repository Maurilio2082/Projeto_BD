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

    public Paciente(int idPaciente, String nomePaciente, String dataNascimento, String email, String telefone, String cpf,
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

    @Override
    public String toString() {
        return String.format("%-10s %-30s %-15s %-30s %-20s %-30s %-20s %-15s %-15s %-15s",
                idPaciente, nomePaciente, cpf, email, telefone,
                idEndereco.getLogradouro(), idEndereco.getNumero(),
                idEndereco.getBairro(), idEndereco.getCidade(), idEndereco.getEstado());
    }
}
