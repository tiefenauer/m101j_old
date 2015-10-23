package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static course.homework.util.Helpers.printJson;

/**
 * Homework 2.2: Find all exam scores greater than or equal to 65, and sort those scores from lowest to highest.
 * What is the student_id of the lowest exam score above 65?
 * Created by danieltiefenauer on 23.10.2015.
 */
public class Homework22 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> coll = db.getCollection("grades");

        Document result = coll.find(gte("score", 65))
                                    .sort(orderBy(ascending("score")))
                                    .first();

        printJson(result);
    }

}
