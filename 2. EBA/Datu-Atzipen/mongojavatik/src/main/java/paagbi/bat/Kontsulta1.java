package paagbi.bat;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Kontsulta1 {
    public static void main(String[] args) {
        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            for (Document d : collection.find(eq("year", 1920))) {
                System.out.println("Izenburua: " + d.getString("title"));
                System.out.println("Urtea: " + d.getInteger("year"));
                System.out.println("---------------------------");
            }
        }
    }
}
