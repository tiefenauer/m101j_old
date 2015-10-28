package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static course.homework.util.Helpers.printJson;

/**
 * Homework 2.3: Write a program in the language of your choice that will remove the grade of type "homework" with the
 * lowest score for each student from the dataset that you imported in the previous homework. Since each document is
 * one grade, it should remove one document per student.
 *
 * Hint/spoiler: If you select homework grade-documents, sort by student and then by score, you can iterate through and
 * find the lowest score for each student by noticing a change in student id. As you notice that change of student_id,
 * remove the document.
 *
 * To verify that you have completed this task correctly, provide the identity of the student with the highest average
 * in the class with following query that uses the aggregation framework. The answer will appear in the _id field of
 * the resulting document.
 *
 * <code>db.grades.aggregate({'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}}, {'$sort':{'average':-1}}, {'$limit':1})</code>
 * Created by danieltiefenauer on 23.10.2015.
 */
public class Homework23 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> coll = db.getCollection("grades");

        ArrayList<Document> result = coll.find(eq("type", "homework"))
                            .sort(orderBy(ascending("student_id", "score")))
                            .into(new ArrayList<Document>());

        /**
         * Delete the lowest
         */
        int studentId = Integer.MIN_VALUE;
        double score = Double.MAX_VALUE;
        for(Document res : result){
            Integer id = res.getInteger("student_id");
            if (id.intValue() != studentId){
                studentId = id.intValue();
                printJson(res);
                coll.deleteOne(res);
            }
        }
    }
}
