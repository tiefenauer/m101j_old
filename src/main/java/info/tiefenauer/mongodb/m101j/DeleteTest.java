package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class DeleteTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("updateTest");

        collection.drop();

        for (int i=0;i<10;i++){
            collection.insertOne(new Document().append("_id", i));
        }

//        collection.deleteMany(gt("_id", 4));
        collection.deleteMany(eq("_id", 4));

        for(Document cur: collection.find().into(new ArrayList<Document>())){
            printJson(cur);
        }
    }
}
