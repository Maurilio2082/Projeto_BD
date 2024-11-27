package controller;

import com.mongodb.client.MongoCollection;
import conexion.DatabaseConfig;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class HistoricoController {

    private final MongoCollection<Document> historicosCollection;

    public HistoricoController() {
        this.historicosCollection = DatabaseConfig.getDatabase().getCollection("historicos");
    }

    public void cadastrarHistorico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Cadastro de Histórico:");
        System.out.print("Data da consulta (YYYY-MM-DD): ");
        String dataConsulta = scanner.nextLine();
        System.out.print("Observação: ");
        String observacao = scanner.nextLine();
        System.out.print("ID do paciente: ");
        String pacienteId = scanner.nextLine();
        System.out.print("ID do médico: ");
        String medicoId = scanner.nextLine();
        System.out.print("ID do hospital: ");
        String hospitalId = scanner.nextLine();
        System.out.print("ID da especialidade: ");
        String especialidadeId = scanner.nextLine();

        Document historico = new Document("dataConsulta", dataConsulta)
                .append("observacao", observacao)
                .append("pacienteId", new ObjectId(pacienteId))
                .append("medicoId", new ObjectId(medicoId))
                .append("hospitalId", new ObjectId(hospitalId))
                .append("especialidadeId", new ObjectId(especialidadeId));

        historicosCollection.insertOne(historico);
        System.out.println("Histórico cadastrado com sucesso!");
    }

    public void atualizarHistorico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do histórico que deseja atualizar: ");
        String idHistorico = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idHistorico));
        Document historicoAtual = historicosCollection.find(filtro).first();

        if (historicoAtual == null) {
            System.out.println("Histórico não encontrado.");
            return;
        }

        System.out.println("Deixe os campos em branco para não alterar o valor atual.");
        System.out.print("Nova data da consulta (YYYY-MM-DD): ");
        String novaDataConsulta = scanner.nextLine();
        System.out.print("Nova observação: ");
        String novaObservacao = scanner.nextLine();

        Document atualizacao = new Document();
        if (!novaDataConsulta.isBlank()) {
            atualizacao.append("dataConsulta", novaDataConsulta);
        }
        if (!novaObservacao.isBlank()) {
            atualizacao.append("observacao", novaObservacao);
        }

        if (atualizacao.isEmpty()) {
            System.out.println("Nenhuma alteração realizada.");
            return;
        }

        historicosCollection.updateOne(filtro, new Document("$set", atualizacao));
        System.out.println("Histórico atualizado com sucesso.");
    }

    public void deletarHistorico() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do histórico que deseja excluir: ");
        String idHistorico = scanner.nextLine();

        Document filtro = new Document("_id", new ObjectId(idHistorico));
        Document historicoAtual = historicosCollection.find(filtro).first();

        if (historicoAtual == null) {
            System.out.println("Histórico não encontrado.");
            return;
        }

        historicosCollection.deleteOne(filtro);
        System.out.println("Histórico excluído com sucesso.");
    }
}
