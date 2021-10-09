package OfflineQuotes.dbUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class mongoCrud {
    public static MongoDatabase connectToDB(){
        try {
            final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
//            mongo.client.uri: mongodb://${FINANCIAL_PLANNER_DB_HOST:localhost}:${FINANCIAL_PLANNER_DB_PORT:27017}/BIA

            final MongoDatabase database = mongoClient.getDatabase("BIA");
            return database;
        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
        return null;
    }

    private static MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase database = connectToDB();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }

    public static void updateDocument(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject record = (JSONObject) parser.parse(data);
        String misID = (String) record.get("misID");
        int count  = Math.toIntExact(getDocumentCountBymisID(misID));
        if (count != 0) {
            MongoCollection<Document> collection = getCollection("BranchIssuanceAnalysis");
            collection.deleteOne(getFilterQuery("misID", misID));
        }
        insertDocument(data);
    }


    public static void insertDocument(String data) throws ParseException {
        //Insert a document into the "characters" collection.
        JSONParser parser = new JSONParser();
        JSONObject record = (JSONObject) parser.parse(data);
        String misID = (String) record.get("misID");
        int count  = Math.toIntExact(getDocumentCountBymisID(misID));
        if(count == 0) {
            MongoCollection<Document> collection = getCollection("BranchIssuanceAnalysis");
            try {
                Object obj = parser.parse(data);
                Document doc = Document.parse(obj.toString());
                collection.insertOne(doc);
            } catch (MongoWriteException | ParseException mwe) {
    //            if (mwe.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
    //                System.out.println("Document with that id already exists");
    //            }
            }
        }else{
            System.out.println("Records exists already::");
        }
    }

    public static FindIterable<Document> getDocumentBymisID(String misID){
        MongoCollection<Document> collection = getCollection("BranchIssuanceAnalysis");
        Document doc = Document.parse("{\"misID\": \""+misID+"\"}");
        FindIterable<Document> result = collection.find(doc);
        return result;
    }

    public static Document getFilterQuery(String key, String value){
        return Document.parse("{\""+key+"\": \""+value+"\"}");
    }

    public static Long getDocumentCountBymisID(String misID){
        MongoCollection<Document> collection = getCollection("BranchIssuanceAnalysis");
        Document doc = Document.parse("{\"misID\": \""+misID+"\"}");
        Long count = collection.countDocuments(doc);
        return count;
    }
}
