//package PaymentIntegrationexample.config;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.connection.SocketSettings;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
//    @Override
//    protected String getDatabaseName() {
//        return "NewDB";
//    }
//
//    @Override
//    public MongoClientSettings mongoClientSettings() {
//        ConnectionString connectionString = new ConnectionString("mongodb+srv://yvbaranwal:yash123@yash.cvke2yt.mongodb.net/NewDB?retryWrites=true&w=majority&appName=Yash");
//
//        return MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .applyToSocketSettings(builder -> 
//                    builder.connectTimeout(30, TimeUnit.SECONDS)
//                           .readTimeout(30, TimeUnit.SECONDS))
//                .build();
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongoClient(), getDatabaseName());
//    }
//}
