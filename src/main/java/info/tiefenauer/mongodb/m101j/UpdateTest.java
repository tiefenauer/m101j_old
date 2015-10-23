package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class UpdateTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("updateTest");

        collection.drop();

        for (int i=0;i<10;i++){
            collection.insertOne(new Document().append("_id", i)
                                               .append("x", i));
        }

//        collection.replaceOne(eq("x", 5), new Document("_id", 5)
//                                                .append("x", 10)
//                                                .append("updated", true));

//        collection.updateOne(eq("x", 5), new Document("$set", new Document("x", 20)));
//        collection.updateOne(eq("_id", 9), new Document("$set", new Document("x", 20)),
//                new UpdateOptions().upsert(true));

        collection.updateMany(gte("_id", 5), new Document("$inc", new Document("x", 1)));

        for (Document cur : collection.find().into(new ArrayList<Document>())){
            printJson(cur);
        }
    }
}
