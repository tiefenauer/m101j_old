import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Daniel on 28.10.2015.
 */
public class HW31 {

    public static void main(String[] args) {
        // Boilerplate
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("school");
        MongoCollection<Document> coll = db.getCollection("students");

        // Iterate over all students
        ArrayList<Document> students = coll.find().into(new ArrayList<Document>());
        for (Document student : students){
            // get scores for each student
            ArrayList<Document> scores = student.get("scores", ArrayList.class);
            // find lowest score for each student
            double minValue = Double.MAX_VALUE;
            Document minScore = null;
            for(Document score : scores){
                String scoreType = score.getString("type");
                double scoreValue = score.getDouble("score");
                if ("homework".equals(scoreType) && scoreValue < minValue){
                    minValue = scoreValue;
                    minScore = score;
                }
            }

            // remove lowest score from scores (may be null, if there was no score with type="homework"
            if (minScore != null){
                System.out.println("removing score: ");
                scores.remove(minScore);
                student.replace("scores", scores);
            }

            // drop-create whole collection. This will replace the existing collection with the pruned in memory
            coll.drop();
            db.createCollection("students");
            db.getCollection("students").insertMany(students);
        }
    }
}

