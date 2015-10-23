package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class FindTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");

        coll.drop();

        for (int i=0; i<10;i++){
            coll.insertOne(new Document("x", i));
        }

        System.out.println("Find one:");
        Document first = coll.find().first();
        printJson(first);

        System.out.println("Find all with into: ");
        List<Document> all = coll.find().into(new ArrayList<Document>());
        for(Document cur : all){
            printJson(first);
        }

        System.out.println("Find all with iteration: ");
        MongoCursor<Document> cursor = coll.find().iterator();
        while(cursor.hasNext()){
            Document cur = cursor.next();
            printJson(cur);
        }
        cursor.close();

        System.out.println("Count: ");
        long count = coll.count();
        System.out.println(count);
    }


}
