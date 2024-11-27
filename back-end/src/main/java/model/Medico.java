package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicos") // Nome da coleção no MongoDB
public class Medico {

    @Id
    private String id; // Identificador único no MongoDB
    private String nome;
    private String conselho;
    private Especialidade especialidade; // Associação direta com Especialidade

    public Medico() {
    }

    public Medico(String id, String nome, String conselho, Especialidade especialidade) {
        this.id = id;
        this.nome = nome;
        this.conselho = conselho;
        this.especialidade = especialidade;
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

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Medico [id=" + id + ", nome=" + nome + ", conselho=" + conselho + ", especialidade=" + especialidade + "]";
    }
}
