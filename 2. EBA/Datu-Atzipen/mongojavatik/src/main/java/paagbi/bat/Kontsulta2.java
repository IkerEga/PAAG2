package paagbi.bat;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Kontsulta2 {
    public static void main(String[] args) {
        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> movies = database.getCollection("movies");

            System.out.println("\nMovies directed by Quentin Tarantino:");

            FindIterable<Document> res = movies.find(eq("directors", "Quentin Tarantino"))
                    .projection(fields(include("title", "year"), excludeId()))
                    .limit(5);

            for (Document d : res) {
                System.out.println(d.toJson());
            }
        }
    }
}
