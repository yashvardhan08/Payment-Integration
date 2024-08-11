package PaymentIntegrationexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cashfree.Cashfree;

@Configuration
public class CashfreeConfig {

    static {
        Cashfree.XClientId = "12520677ba14a4b8c8c114bd80602521";
        Cashfree.XClientSecret = "9b27b6730e0211aa3620b639dd25e25a773934ba";
        Cashfree.XEnvironment = Cashfree.SANDBOX;
    }

    @Bean
    public Cashfree cashfree() {
        return new Cashfree();
    }
}
