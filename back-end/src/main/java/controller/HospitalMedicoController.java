package controller;

import com.mongodb.client.MongoCollection;
import conexion.DatabaseConfig;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class HospitalMedicoController {

    private final MongoCollection<Document> hospitalMedicoCollection;

    public HospitalMedicoController() {
        this.hospitalMedicoCollection = DatabaseConfig.getDatabase().getCollection("hospital_medico");
    }

    public void cadastrarMedicoXHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do hospital: ");
        String hospitalId = scanner.nextLine();
        System.out.print("ID do médico: ");
        String medicoId = scanner.nextLine();

        Document relacao = new Document("hospitalId", new ObjectId(hospitalId))
                .append("medicoId", new ObjectId(medicoId));

        hospitalMedicoCollection.insertOne(relacao);
        System.out.println("Relação médico-hospital cadastrada com sucesso.");
    }

    public void atualizarMedicoXHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação médico-hospital que deseja atualizar: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        Document relacaoAtual = hospitalMedicoCollection.find(filtro).first();

        if (relacaoAtual == null) {
            System.out.println("Relação não encontrada.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Novo ID do hospital: ");
        String novoHospitalId = scanner.nextLine();
        System.out.print("Novo ID do médico: ");
        String novoMedicoId = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novoHospitalId.isBlank()) {
            atualizacao.append("hospitalId", new ObjectId(novoHospitalId));
        }
        if (!novoMedicoId.isBlank()) {
            atualizacao.append("medicoId", new ObjectId(novoMedicoId));
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        hospitalMedicoCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Relação médico-hospital atualizada com sucesso.");
    }

    public void deletarMedicoXHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da relação médico-hospital que deseja excluir: ");
        String idRelacao = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idRelacao));
        hospitalMedicoCollection.deleteOne(filtro);
        System.out.println("Relação médico-hospital excluída com sucesso.");
    }
}
