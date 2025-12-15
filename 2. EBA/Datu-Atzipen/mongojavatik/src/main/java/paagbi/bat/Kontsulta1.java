package paagbi.bat;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Kontsulta1 {
    public static void main(String[] args) {
        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> movies = database.getCollection("movies");

            System.out.println("Movies released in 1920:");

            FindIterable<Document> res = movies.find(eq("year", 1920))  //Filtro por año
                    .projection(fields(include("title", "year"), excludeId()))  //Agregar los campos que queremos mostrar
                    .limit(5);  //Para no imprimir demasiados resultados

            for (Document d : res) {
                System.out.println(d.toJson());
            }
        }
    }
}
