package PaymentIntegrationexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import PaymentIntegrationexample.link.CreateLink;
import PaymentIntegrationexample.service.PaymentService;

@SpringBootApplication
public class PaymentIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentIntegrationApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(PaymentService paymentService, CreateLink createLink) {
        return args -> {
            String orderId = paymentService.createOrder(1.00, "INR", "123", "9999999999");
            System.out.println("Order ID: " + orderId);

            // Get checkout URL
            String checkoutUrl = paymentService.getSessionId(orderId);
            System.out.println("SessionId: " + checkoutUrl);

            createLink.createPaymentLink(orderId);
        };
    }
}
