package brightlogic.globapay.dto.response;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PaymentTransactionResponse {
    private UUID transactionId;
    private UUID userId;
    private BigDecimal amount;
    private CurrencyType currency;
    private PaymentMethodType paymentMethod;
    private PaymentStatus status;
    private Instant createdAt;
    private double exchangeRate;
    private BigDecimal amountInBaseCurrency;
    private String idempotencyKey;

    public PaymentTransactionResponse(UUID transactionId, UUID userId, BigDecimal amount, CurrencyType currency, PaymentMethodType paymentMethod, PaymentStatus status, Instant createdAt,
                                      double exchangeRate, BigDecimal amountInBaseCurrency, String idempotencyKey) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.exchangeRate = exchangeRate;
        this.amountInBaseCurrency = amountInBaseCurrency;
        this.idempotencyKey = idempotencyKey;
    }

    public PaymentTransactionResponse(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentTransactionResponse() {
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getAmountInBaseCurrency() {
        return amountInBaseCurrency;
    }

    public void setAmountInBaseCurrency(BigDecimal amountInBaseCurrency) {
        this.amountInBaseCurrency = amountInBaseCurrency;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
