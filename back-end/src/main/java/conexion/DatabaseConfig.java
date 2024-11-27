package conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConfig {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "labdatabase";

    private static MongoDatabase database;

    static {
        try {
            MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Conectado ao MongoDB: " + DATABASE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao conectar ao MongoDB.");
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
