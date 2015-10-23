package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class FindWithSortSkipLimitTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithSortrTest");

        collection.drop();

        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                collection.insertOne(new Document().append("i",i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());

        /**
         * Sorting
         */
//        Bson sort = new Document("i", 1).append("j", -1);
        Bson sort = orderBy(ascending("i"), descending("j"));

        List<Document> all = collection.find()
                                .projection(projection)
                                .sort(sort)
                                .skip(20)
                                .limit(50)
                                .into(new ArrayList<Document>());

        for (Document cur : all){
            printJson(cur);
        }

        long count = collection.count();
        System.out.println();
        System.out.println(count);
    }
}
