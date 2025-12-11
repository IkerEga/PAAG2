package paagbi.bat;

import static com.mongodb.client.model.Filters.near;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

public class Kontsulta6 {
    public static void main(String[] args) {

        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("theaters");

            // 1. Buscar un cine específico (por ejemplo, el ID 1)
            Document theater = collection.find(new Document("theaterId", 164)).first();

            if (theater == null) {
                System.out.println("Ez da aurkitu zinema hori.");
                return;
            }

            
            List<Double> coords = theater
                    .get("location", Document.class)
                    .get("geo", Document.class)
                    .getList("coordinates", Double.class);

            double lon = coords.get(0);
            double lat = coords.get(1);

            System.out.println("Bilatzen hurbil dauden zinemak honetatik:");
            System.out.println("Theater ID: " + theater.get("theaterId"));
            System.out.println("Koordenatuak: " + lon + ", " + lat);
            System.out.println("---------------------------");

            Point point = new Point(new Position(lon, lat));

            MongoCursor<Document> cursor = collection.find(
                    near("location.geo", point, 1000.0, 0.0)
            ).iterator();

            while (cursor.hasNext()) {
                Document d = cursor.next();

                Document address = d.get("location", Document.class)
                                    .get("address", Document.class);

                System.out.println("Theater ID: " + d.get("theaterId"));
                System.out.println("Helbidea:");
                System.out.println("  Kalea: " + address.getString("street1"));
                System.out.println("  Hiria: " + address.getString("city"));
                System.out.println("  Estatua: " + address.getString("state"));
                System.out.println("  Zip: " + address.getString("zipcode"));
                System.out.println("---------------------------");
            }
        }
    }
}
