package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitais") // Nome da coleção no MongoDB
public class Hospital {

    @Id
    private String id; // Identificador único no MongoDB
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String categoria;
    private Endereco endereco; // Associação direta com Endereco

    public Hospital() {
    }

    public Hospital(String id, String razaoSocial, String cnpj, String email, String telefone, String categoria, Endereco endereco) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.categoria = categoria;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Hospital [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj +
               ", email=" + email + ", telefone=" + telefone + ", categoria=" + categoria +
               ", endereco=" + endereco + "]";
    }
}
