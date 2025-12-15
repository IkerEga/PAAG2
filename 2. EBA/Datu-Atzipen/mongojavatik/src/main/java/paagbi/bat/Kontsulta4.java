package paagbi.bat;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.and;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Kontsulta4 {
    public static void main(String[] args) {

        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> movies = database.getCollection("movies");

            System.out.println("\nMovies from Spain released after 2014");

            FindIterable<Document> res = movies.find(
                    and(
                            eq("countries", "Spain"),
                            gt("year", 2014)))
                    .projection(fields(include("title", "year", "countries"), exclude("_id")))
                    .limit(5);

            for (Document d : res) {
                System.out.println(d.toJson());
            }
        }
    }
}
