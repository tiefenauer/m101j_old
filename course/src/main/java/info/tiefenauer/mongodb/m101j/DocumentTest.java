package info.tiefenauer.mongodb.m101j;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

import static info.tiefenauer.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by danieltiefenauer on 21.10.2015.
 */
public class DocumentTest {

    public static void main(String[] args) {
        Document doc = new Document()
                .append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("long", 1L)
                .append("double", 1.1)
                .append("b", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1,2,3))
                ;

        String str = doc.getString("str");
        printJson(doc);
    }
}
