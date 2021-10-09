//package OfflineQuotes.dbUtils;
//
//
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class MongoService {
//
//    @Resource(name = "mongoTemplateMap")
//    private Map<String, MongoTemplate> mtMap;
//
//    @Autowired
//    @Qualifier("turtleMongoTemplate")
//    private MongoTemplate              turtleMongoTemplate;
//
//
//    protected MongoTemplate getMongoTemplate(String collectionName) {
//
//        try {
//
//            String key = MasterCollectionUtils.determineMasterCollections(collectionName);
//            if (key == null) {
//                try {
//                    key = session().get(BrokerConstants.BROKER);
//                }
//                catch (Exception e) {
//                    key = defaultBroker;
//                }
//
//                if (key == null) {
//                    throw new Exception("broker key is null");
//                }
//            }
//            if (mtMap.get(key) == null) {
//                throw new Exception("broker key: {" + key + "} not found in our system");
//            }
//            return new MongoTemplate(new SimpleMongoClientDatabaseFactory("", "", ""));
//
//        }
//        catch (Exception e) {
//
//
//        }
//        return null;
//    }
//
//    public void save(String collectionName, Object object) {
//        getMongoTemplate(collectionName).save(object, collectionName);
//    }
//
//    public List<Object> getAllRecords(String collectionName) {
//        return getMongoTemplate(collectionName).findAll(Object.class, collectionName);
//    }
//
//    public void delete(String collectionName, Query query) {
//        getMongoTemplate(collectionName).remove(query, collectionName);
//    }
//
//    public <T> List<T> find(String collectionName, Query query, Class<T> classz) {
//        return getMongoTemplate(collectionName).find(query, classz, collectionName);
//    }
//
//    public <T> T findOne(String collectionName, Query query, Class<T> classz) {
//        return getMongoTemplate(collectionName).findOne(query, classz, collectionName);
//    }
//
//    public int update(String collectionName, Query query, Map keyValueMap) {
//        Update update = new Update();
//        keyValueMap.forEach((key, value) -> {
//            update.set(key.toString(), value);
//        });
//        return getMongoTemplate(collectionName).updateMulti(query, update, collectionName).getN();
//    }
//}
