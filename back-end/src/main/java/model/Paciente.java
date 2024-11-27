package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pacientes") // Nome da coleção no MongoDB
public class Paciente {

    @Id
    private String id; // Identificador único no MongoDB
    private String nome;
    private String email;
    private String telefone;
    private String dataNascimento;
    private String cpf;
    private Endereco endereco; // Associação direta com Endereco

    public Paciente() {
    }

    public Paciente(String id, String nome, String email, String telefone, String dataNascimento, String cpf, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", nome=" + nome + ", email=" + email +
               ", telefone=" + telefone + ", dataNascimento=" + dataNascimento +
               ", cpf=" + cpf + ", endereco=" + endereco + "]";
    }
}
