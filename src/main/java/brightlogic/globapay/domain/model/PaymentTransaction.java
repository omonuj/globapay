package brightlogic.globapay.domain.model;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Table(name = "payment_transactions")
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;

    private BigDecimal amount;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    // Changed from Embedded PaymentMethod to Enum PaymentMethodType
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @ManyToOne
    private UserAccount user;

    private double exchangeRate;

    private BigDecimal amountInBaseCurrency;

    private UUID userId;

    public PaymentTransaction(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentTransaction() {
    }

    public PaymentTransaction(UUID transactionId, BigDecimal amount, String idempotencyKey, LocalDateTime createdAt, LocalDateTime updatedAt, PaymentMethodType paymentMethod, PaymentStatus status, CurrencyType currency,
                              UserAccount user, double exchangeRate, BigDecimal amountInBaseCurrency, UUID userId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.currency = currency;
        this.user = user;
        this.exchangeRate = exchangeRate;
        this.amountInBaseCurrency = amountInBaseCurrency;
        this.userId = userId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
