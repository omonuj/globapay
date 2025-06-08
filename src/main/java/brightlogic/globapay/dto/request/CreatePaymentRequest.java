package brightlogic.globapay.dto.request;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreatePaymentRequest {

    @NotNull
    private UUID userId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull
    private CurrencyType currency;

    @NotNull
    private PaymentMethodType paymentMethod;

    private String idempotencyKey;
}
