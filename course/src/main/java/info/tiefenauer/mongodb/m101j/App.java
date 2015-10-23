package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by danieltiefenauer on 21.10.2015.
 */
public class App {

    public static void main(String[] args) {
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient client =  new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());

        MongoCollection<Document> coll = db.getCollection("test");
    }
}
