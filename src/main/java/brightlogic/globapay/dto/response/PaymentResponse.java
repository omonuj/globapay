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
public class PaymentResponse {
    private UUID transactionId;
    private UUID userId;
    private BigDecimal amount;
    private CurrencyType currency;
    private PaymentMethodType paymentMethod;
    private PaymentStatus status;
    private Instant createdAt;
}
