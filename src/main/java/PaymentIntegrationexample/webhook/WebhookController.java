//package PaymentIntegrationexample.webhook;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/webhook")
//public class WebhookController {
//
//    @PostMapping
//    public ResponseEntity<String> handlePaymentStatusWebhook(
////            @RequestHeader Map<String, String> headers,
//            @RequestBody Map<String, Object> payload) {
//
//        // Log headers and payload for debugging
////        System.out.println("Received webhook with headers: " + headers);
//        System.out.println("Received webhook with payload: " + payload);
//
//        // Extract necessary information from the payload
//        String orderId = (String) payload.get("orderId");
//        String paymentStatus = (String) payload.get("paymentStatus");
//
//        // Handle the payment status update
//        processPaymentStatus(orderId, paymentStatus);
//
//        // Respond to the webhook
//        return ResponseEntity.ok("Webhook received successfully");
//    }
//
//    private void processPaymentStatus(String orderId, String paymentStatus) {
//        // Implement your logic to handle the payment status update
//        System.out.println("Processing payment status for order: " + orderId + " with status: " + paymentStatus);
//
//        // You can update the payment status in your database or perform other necessary actions
//    }
//}
