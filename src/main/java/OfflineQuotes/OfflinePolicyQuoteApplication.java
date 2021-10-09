package OfflineQuotes;

import java.util.List;

import javax.annotation.PostConstruct;

//import com.turtle.beans.models.broker.BrokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
//import com.turtlemint.fp.configuration.MongoAuditor;
//import com.turtlemint.fp.configuration.MultiTenantMongoDbFactory;
//import com.turtlemint.fp.utils.BrokerUtils;


@SpringBootApplication
public class OfflinePolicyQuoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfflinePolicyQuoteApplication.class, args);
    }
}
