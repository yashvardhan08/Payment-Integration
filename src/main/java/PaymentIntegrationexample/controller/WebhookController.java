//package PaymentIntegrationexample.controller;
//
//import PaymentIntegrationexample.model.PaymentLink;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//
//@RestController
//public class WebhookController {
//
//    @Value("${cashfree.clientId}")
//    private String clientId;
//
//    @Value("${cashfree.clientSecret}")
//    private String clientSecret;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @PostMapping("/webhook/payment-status")
//    public String handlePost(@RequestBody String rawBody,
//                             @RequestHeader("x-webhook-signature") String providedSignature,
//                             @RequestHeader("x-webhook-timestamp") String timestamp) {
//        try {
//            // Highlight: Verify the signature
//            String computedSignature = generateSignature(timestamp, rawBody);
//            if (!computedSignature.equals(providedSignature)) {
//                return "Webhook verification failed";
//            }
//
//            System.out.println("Received webhook payload: " + rawBody);
//
//            // Parse the incoming JSON payload
//            JSONObject json = new JSONObject(rawBody);
//            JSONObject paymentData = json.getJSONObject("data").getJSONObject("payment");
//            String orderId = json.getJSONObject("data").getJSONObject("order").getString("order_id");
//
//            // Find the corresponding PaymentLink document in MongoDB
//            Query query = new Query();
//            query.addCriteria(Criteria.where("orderId").is(orderId));
//            PaymentLink paymentLink = mongoTemplate.findOne(query, PaymentLink.class);
//
//            if (paymentLink != null) {
//                // Update the PaymentLink document with the payment information
//                paymentLink.setCf_payment_id(paymentData.getString("cf_payment_id"));
//                paymentLink.setPaymentStatus(paymentData.getString("payment_status"));
//                paymentLink.setPaymentAmount(paymentData.getDouble("payment_amount"));
//                paymentLink.setPaymentCurrency(paymentData.getString("payment_currency"));
//                paymentLink.setPaymentMessage(paymentData.getString("payment_message"));
//                paymentLink.setPaymentTime(paymentData.getString("payment_time"));
//                paymentLink.setBankReference(paymentData.getString("bank_reference"));
//                paymentLink.setUpiId(paymentData.getJSONObject("payment_method").getJSONObject("upi").getString("upi_id"));
//
//                // Save the updated PaymentLink document back to MongoDB
//                mongoTemplate.save(paymentLink);
//                return "Webhook processed successfully";
//            } else {
//                return "Order ID not found";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Webhook processing failed";
//        }
//    }
//
//    // Highlight: Method to generate signature
//    public String generateSignature(String timestamp, String payload) throws Exception {
//        String data = timestamp + payload;
//        String secretKey = "SECRET-KEY"; // Get secret key from Cashfree Merchant Dashboard;
//        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
//        sha256HMAC.init(secretKeySpec);
//        return Base64.getEncoder().encodeToString(sha256HMAC.doFinal(data.getBytes()));
//    }
//}

package PaymentIntegrationexample.controller;

import PaymentIntegrationexample.model.PaymentLink;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Value("${cashfree.clientId}")
    private String clientId;

    @Value("${cashfree.clientSecret}")
    private String clientSecret;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/webhook/payment-status")
    public String handlePost(@RequestBody String rawBody,
                             @RequestHeader("x-webhook-key") String key,
                             @RequestHeader("x-webhook-timestamp") String timestamp) {
        try {
            // Parse the incoming JSON payload
            JSONObject json = new JSONObject(rawBody);
            JSONObject data = json.getJSONObject("data");
            JSONObject payment = data.getJSONObject("payment");
            JSONObject order = data.getJSONObject("order");

            String orderId = order.getString("order_id");

            // Find the corresponding PaymentLink document in MongoDB
            Query query = new Query();
            query.addCriteria(Criteria.where("orderId").is(orderId));
            PaymentLink paymentLink = mongoTemplate.findOne(query, PaymentLink.class);

            if (paymentLink != null) {
                
                paymentLink.setCf_payment_id(payment.getString("cf_payment_id"));
                paymentLink.setPaymentStatus(payment.getString("payment_status"));
                paymentLink.setPaymentAmount(payment.getDouble("payment_amount"));
                paymentLink.setPaymentCurrency(payment.getString("payment_currency"));
                paymentLink.setPaymentMessage(payment.getString("payment_message"));
                paymentLink.setPaymentTime(payment.getString("payment_time"));
                paymentLink.setBankReference(payment.optString("bank_reference", null));
                paymentLink.setPaymentGroup(payment.optString("payment_group", null));

                // Handling different payment methods
                if (payment.has("payment_method")) {
                    JSONObject paymentMethod = payment.getJSONObject("payment_method");
                    if (paymentMethod.has("upi")) {
                        paymentLink.setUpiId(paymentMethod.getJSONObject("upi").getString("upi_id"));
                    } else if (paymentMethod.has("card")) {
                        JSONObject card = paymentMethod.getJSONObject("card");
                        paymentLink.setCardNumber(card.getString("card_number"));
                        paymentLink.setCardNetwork(card.getString("card_network"));
                        paymentLink.setCardType(card.getString("card_type"));
                        paymentLink.setCardSubType(card.getString("card_sub_type"));
                        paymentLink.setCardCountry(card.getString("card_country"));
                        paymentLink.setCardBankName(card.getString("card_bank_name"));
                        paymentLink.setCardNetworkReferenceId(card.getString("card_network_reference_id"));
                    } else if (paymentMethod.has("netbanking")) {
                        JSONObject netbanking = paymentMethod.getJSONObject("netbanking");
                        paymentLink.setNetbankingBankCode(netbanking.getString("netbanking_bank_code"));
                        paymentLink.setNetbankingBankName(netbanking.getString("netbanking_bank_name"));
                    } else if (paymentMethod.has("app")) {
                        paymentLink.setUpiId(paymentMethod.getJSONObject("app").getString("upi_id"));
                    }
                }

                // Handle error details if present
                if (data.has("error_details")) {
                    JSONObject errorDetails = data.getJSONObject("error_details");
                    paymentLink.setErrorCode(errorDetails.getString("error_code"));
                    paymentLink.setErrorDescription(errorDetails.getString("error_description"));
                    paymentLink.setErrorReason(errorDetails.getString("error_reason"));
                    paymentLink.setErrorSource(errorDetails.getString("error_source"));
                }

                
                mongoTemplate.save(paymentLink);
                return "Webhook processed successfully";
            } else {
                return "Order ID not found";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Webhook processing failed";
        }
    }
}
