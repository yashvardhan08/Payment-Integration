package PaymentIntegrationexample.link;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.cashfree.ApiException;
import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.OrderEntity;

import PaymentIntegrationexample.model.PaymentLink;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CreateLink {
    
    @Autowired
    private Cashfree cashfreeClient;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String API_URL = "https://sandbox.cashfree.com/pg/links";
    private static final String CLIENT_ID = "12520677ba14a4b8c8c114bd80602521";
    private static final String CLIENT_SECRET = "9b27b6730e0211aa3620b639dd25e25a773934ba";
    private static final String API_VERSION = "2022-09-01";

    public void createPaymentLink(String orderId) {
        OrderEntity order = fetchOrder(orderId);
        String requestBody = buildRequestBody(order);
        sendHttpRequest(requestBody);
    }

    private OrderEntity fetchOrder(String orderId) {
        try {
            ApiResponse<OrderEntity> response = cashfreeClient.PGFetchOrder(API_VERSION, orderId, null, null, null);
            return response.getData();
        } catch (ApiException e) {
            throw new RuntimeException("Error fetching order: " + e.getMessage(), e);
        }
    }

    private String buildRequestBody(OrderEntity order) {
        String customerPhone = order.getCustomerDetails() != null && order.getCustomerDetails().getCustomerPhone() != null
                ? order.getCustomerDetails().getCustomerPhone()
                : "unknown";
        String orderIdString = order.getOrderId() != null ? order.getOrderId() : "unknown";
        String orderAmount = order.getOrderAmount() != null ? order.getOrderAmount().toString() : "0";
        String orderCurrency = order.getOrderCurrency() != null ? order.getOrderCurrency() : "INR";
        String orderTags = order.getOrderTags() != null ? order.getOrderTags().toString() : "Payment";
        String returnUrl = order.getOrderMeta() != null && order.getOrderMeta().getReturnUrl() != null
                ? order.getOrderMeta().getReturnUrl()
                : "";
        String paymentMethods = order.getOrderMeta() != null && order.getOrderMeta().getPaymentMethods() != null
                ? order.getOrderMeta().getPaymentMethods().toString()
                : "";
        
        saveToMongoDB(customerPhone, orderIdString, Double.parseDouble(orderAmount), orderCurrency, orderTags, returnUrl, paymentMethods);
        
        return "{\n" +
                "  \"customer_details\": {\n" +
                "    \"customer_phone\": \"" + customerPhone + "\"\n" +
                "  },\n" +
                "  \"link_notify\": {\n" +
                "    \"send_sms\": true\n" +
                "  },\n" +
                "  \"link_id\": \"" + orderIdString + "\",\n" +
                "  \"link_amount\": " + orderAmount + ",\n" +
                "  \"link_currency\": \"" + orderCurrency + "\",\n" +
                "  \"link_purpose\": \"" + orderTags + "\",\n" +
                "  \"return_url\": \"" + returnUrl + "\",\n" +
                "  \"payment_methods\": \"" + paymentMethods + "\"\n" +
                "}";
    }

    private void saveToMongoDB(String customerPhone, String orderId, Double orderAmount, String orderCurrency, String orderTags, String returnUrl, String paymentMethods) {
        PaymentLink paymentLink = new PaymentLink();
        paymentLink.setCustomerPhone(customerPhone);
        paymentLink.setOrderId(orderId);
        paymentLink.setOrderAmount(orderAmount);
        paymentLink.setOrderCurrency(orderCurrency);
        paymentLink.setOrderTags(orderTags);
        paymentLink.setReturnUrl(returnUrl);
        paymentLink.setPaymentMethods(paymentMethods);

        mongoTemplate.save(paymentLink);
    }

    private void sendHttpRequest(String requestBody) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("x-api-version", API_VERSION)
                .header("x-client-id", CLIENT_ID)
                .header("x-client-secret", CLIENT_SECRET)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("link_url")) {
                String linkUrl = jsonResponse.getString("link_url");
                System.out.println("Link URL: " + linkUrl);
            } else {
                System.out.println("Link URL not found in response.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
