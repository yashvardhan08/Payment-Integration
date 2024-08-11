package PaymentIntegrationexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cashfree.ApiException;
import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.CreateOrderRequest;
import com.cashfree.model.CustomerDetails;
import com.cashfree.model.OrderEntity;


@Service
public class PaymentService {

    @Autowired
    private Cashfree cashfreeClient;
    

    public String createOrder(double orderAmount, String orderCurrency, String customerId, String customerPhone) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerId(customerId);
        customerDetails.setCustomerPhone(customerPhone);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setOrderAmount(orderAmount);
        request.setOrderCurrency(orderCurrency);
        request.setCustomerDetails(customerDetails);

        try {
            ApiResponse<OrderEntity> response = cashfreeClient.PGCreateOrder("2022-09-01", request, null, null, null);
            return response.getData().getOrderId();
        } catch (ApiException e) {
            throw new RuntimeException("Error creating order: " + e.getMessage(), e);
        }
    }

    public String getSessionId(String orderId) {
        try {
            ApiResponse<OrderEntity> response = cashfreeClient.PGFetchOrder("2022-09-01", orderId, null, null, null);
            OrderEntity order = response.getData();
            System.out.println(order.toString());
            return order.getPaymentSessionId();
        } catch (ApiException e) {
            throw new RuntimeException("Error fetching order: " + e.getMessage(), e);
        }
    }
}
