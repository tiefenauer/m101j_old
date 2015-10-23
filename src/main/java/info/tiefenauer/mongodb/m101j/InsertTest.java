package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;
import static java.util.Arrays.asList;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class InsertTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name", "Smith")
                        .append("age", 30)
                        .append("profession", "programmer");

        Document jones = new Document("name", "Jones")
                        .append("age", 25)
                        .append("profession", "hacker");

        printJson(smith);
        coll.insertMany(asList(smith, jones));
        printJson(smith);
    }
}
