package controller;

import com.mongodb.client.MongoCollection;
import conexion.DatabaseConfig;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class EspecialidadeMedicoController {

    private final MongoCollection<Document> especialidadeMedicoCollection;

    public EspecialidadeMedicoController() {
        this.especialidadeMedicoCollection = DatabaseConfig.getDatabase().getCollection("especialidade_medico");
    }

    public void cadastrarEspecialidadeXMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID da especialidade: ");
        String especialidadeId = scanner.nextLine();
        System.out.print("ID do médico: ");
        String medicoId = scanner.nextLine();

        Document relacao = new Document("especialidadeId", new ObjectId(especialidadeId))
                .append("medicoId", new ObjectId(medicoId));

        especialidadeMedicoCollection.insertOne(relacao);
        System.out.println("Relação especialidade-médico cadastrada com sucesso.");
    }

    public void atualizarEspecialidadeXMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação especialidade-médico que deseja atualizar: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        Document relacaoAtual = especialidadeMedicoCollection.find(filtro).first();

        if (relacaoAtual == null) {
            System.out.println("Relação não encontrada.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Novo ID da especialidade: ");
        String novaEspecialidadeId = scanner.nextLine();
        System.out.print("Novo ID do médico: ");
        String novoMedicoId = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novaEspecialidadeId.isBlank()) {
            atualizacao.append("especialidadeId", new ObjectId(novaEspecialidadeId));
        }
        if (!novoMedicoId.isBlank()) {
            atualizacao.append("medicoId", new ObjectId(novoMedicoId));
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        especialidadeMedicoCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Relação especialidade-médico atualizada com sucesso.");
    }

    public void deletarEspecialidadeXMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação especialidade-médico que deseja excluir: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        especialidadeMedicoCollection.deleteOne(filtro);
        System.out.println("Relação especialidade-médico excluída com sucesso.");
    }
}
