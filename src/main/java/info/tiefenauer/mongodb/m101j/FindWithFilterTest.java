package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class FindWithFilterTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        for (int i=0;i<10;i++){
            collection.insertOne(new Document()
                                    .append("x", new Random().nextInt(2))
                                    .append("y", new Random().nextInt(100))
                                    .append("i", i)
            );
        }

        /**
         * Filterung
         */
//        Bson filter = new Document("x", 0)
//                .append("y", new Document("$gt", 10).append("$lt", 90));

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

        /**
         * Projecting
         */
//        Bson projection = new Document("x", 0).append("_id", 0);
//        Bson projection = new Document("y", 1).append("i", 1);
//        Bson projection = Projections.exclude("x", "_id");
//        Bson projection = Projections.fields(Projections.include("y", "i"),
//                                            Projections.exclude("_id"));
        Bson projection = fields(include("y", "i"),
                                            excludeId());

        List<Document> all = collection.find(filter)
                                        .projection(projection)
                                        .into(new ArrayList<Document>());

        for (Document cur : all){
            printJson(cur);
        }

        long count = collection.count(filter);
        System.out.println();
        System.out.println(count);
    }
}
