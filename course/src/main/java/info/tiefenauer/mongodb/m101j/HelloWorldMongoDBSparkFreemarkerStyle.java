package info.tiefenauer.mongodb.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;

import static spark.Spark.halt;

/**
 * Created by danieltiefenauer on 23.10.2015.
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {

    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/freemarker");

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        final MongoCollection<Document> collection = db.getCollection("hello");

        collection.drop();

        collection.insertOne(new Document("name", "MongoDB"));

        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer = new StringWriter();
                try{
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Document doc = collection.find().first();
                    helloTemplate.process(doc, writer);
                }
                catch (Exception e){
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
