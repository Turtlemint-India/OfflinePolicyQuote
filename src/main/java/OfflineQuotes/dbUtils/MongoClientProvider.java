package OfflineQuotes.dbUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
//        import com.turtlemint.tma.bean.DatabaseDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoClientProvider {

    public MongoClient getMongoClient() {

        List<ServerAddress> serverAddresses = new ArrayList<>();
        serverAddresses.add(new ServerAddress("localhost", Integer.parseInt("27017")));
        MongoClientOptions options = MongoClientOptions.builder().build();
        return new MongoClient(serverAddresses, options);
//        if (StringUtils.isNotBlank("") && StringUtils.isNotBlank("")) {
//
//            MongoCredential credential = MongoCredential.createCredential("",
//                    "", "".toCharArray());
//
//            return new MongoClient(serverAddresses, credential, options);
//
//        } else {
//
//            return new MongoClient(serverAddresses, options);
//
//        }
    }

}