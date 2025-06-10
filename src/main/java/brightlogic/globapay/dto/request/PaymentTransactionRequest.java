package brightlogic.globapay.dto.request;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentTransactionRequest {

    @NotNull
    private UUID userId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull
    private CurrencyType currency;

    @NotNull
    private String paymentMethod;

    private String idempotencyKey;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public @DecimalMin(value = "0.01", message = "Amount must be greater than zero") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@DecimalMin(value = "0.01", message = "Amount must be greater than zero") BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
