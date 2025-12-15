package paagbi.bat;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Sorts.*;

import java.util.List;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Kontsulta7 {

    public static void main(String[] args) {

        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> movies = database.getCollection("movies");

            System.out.println("\nMovies per director:");

            List<Document> pipeline = List.of(
                    new Document("$unwind", "$directors"),
                    new Document("$group", new Document("_id", "$directors")
                            .append("movieCount", new Document("$sum", 1))),
                    new Document("$sort", new Document("movieCount", -1)));

            for (Document d : movies.aggregate(pipeline)) {
                System.out.println(d.toJson());
            }
        }
    }
}
