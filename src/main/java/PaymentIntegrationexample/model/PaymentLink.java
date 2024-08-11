package PaymentIntegrationexample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "payment_links")
public class PaymentLink {

    @Id
    private String id;
    @Indexed
    private String orderId;
    private String customerPhone;
    
    private Double orderAmount;
    private String orderCurrency;
    private String orderTags; // Assuming orderTags is a map, update as needed
    private String returnUrl;
    private String paymentMethods;

    private String paymentStatus;
    private Double paymentAmount;
    private String paymentCurrency;
    private String paymentMessage;
    private String paymentTime;
    private String bankReference;
    private String upiId;
    private String cardNumber;
    private String cardNetwork;
    private String cardType;
    private String cardSubType;
    private String cardCountry;
    private String cardBankName;
    private String cardNetworkReferenceId;
    private String netbankingBankCode;
    private String netbankingBankName;
    private String paymentGroup;
    private String cf_payment_id;
    private String errorCode;
    private String errorDescription;
    private String errorReason;
    private String errorSource;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	public String getOrderTags() {
		return orderTags;
	}
	public void setOrderTags(String orderTags2) {
		this.orderTags = orderTags2;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(String paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getPaymentMessage() {
		return paymentMessage;
	}
	public void setPaymentMessage(String paymentMessage) {
		this.paymentMessage = paymentMessage;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getBankReference() {
		return bankReference;
	}
	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}
	public String getUpiId() {
		return upiId;
	}
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardNetwork() {
		return cardNetwork;
	}
	public void setCardNetwork(String cardNetwork) {
		this.cardNetwork = cardNetwork;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardSubType() {
		return cardSubType;
	}
	public void setCardSubType(String cardSubType) {
		this.cardSubType = cardSubType;
	}
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}
	public String getCardBankName() {
		return cardBankName;
	}
	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}
	public String getCardNetworkReferenceId() {
		return cardNetworkReferenceId;
	}
	public void setCardNetworkReferenceId(String cardNetworkReferenceId) {
		this.cardNetworkReferenceId = cardNetworkReferenceId;
	}
	public String getNetbankingBankCode() {
		return netbankingBankCode;
	}
	public void setNetbankingBankCode(String netbankingBankCode) {
		this.netbankingBankCode = netbankingBankCode;
	}
	public String getNetbankingBankName() {
		return netbankingBankName;
	}
	public void setNetbankingBankName(String netbankingBankName) {
		this.netbankingBankName = netbankingBankName;
	}
	public String getPaymentGroup() {
		return paymentGroup;
	}
	public void setPaymentGroup(String paymentGroup) {
		this.paymentGroup = paymentGroup;
	}
	public String getCf_payment_id() {
		return cf_payment_id;
	}
	public void setCf_payment_id(String cf_payment_id) {
		this.cf_payment_id = cf_payment_id;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getErrorSource() {
		return errorSource;
	}
	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}

    // Getters and setters for all fields
    // ...
}
