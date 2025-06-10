package brightlogic.globapay.service.interfaces;

import java.util.UUID;

public interface AlertService {

    void sendFraudAlert(UUID userId, UUID transactionId, String reason, boolean flagged);

}
