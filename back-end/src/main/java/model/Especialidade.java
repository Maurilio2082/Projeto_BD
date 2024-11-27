package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especialidades") // Nome da coleção no MongoDB
public class Especialidade {

    @Id
    private String id; // Identificador único no MongoDB
    private String nomeEspecialidade;

    public Especialidade() {
    }

    public Especialidade(String id, String nomeEspecialidade) {
        this.id = id;
        this.nomeEspecialidade = nomeEspecialidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

    @Override
    public String toString() {
        return "Especialidade [id=" + id + ", nomeEspecialidade=" + nomeEspecialidade + "]";
    }
}
