package brightlogic.globapay.service.impl;

import brightlogic.globapay.service.interfaces.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AlertServiceImpl implements AlertService {
    @Override
    public void sendFraudAlert(UUID userId, UUID transactionId, String reason, boolean flagged) {
        log.info("📣 ALERT: Fraud alert sent for User ID: {}, Transaction ID: {}, Reason: {}, Flagged: {}",
                userId, transactionId, reason, flagged);
    }
}
