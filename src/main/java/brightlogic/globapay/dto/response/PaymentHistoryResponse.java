package brightlogic.globapay.dto.response;

import brightlogic.globapay.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PaymentHistoryResponse {
    private UUID transactionId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private Instant timestamp;
}
