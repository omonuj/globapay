package brightlogic.globapay.event.listener;

import brightlogic.globapay.domain.enums.AuditEventType;
import brightlogic.globapay.domain.model.AuditLog;
import brightlogic.globapay.event.AuditLogCreatedEvent;
import brightlogic.globapay.event.FraudDetectionAuditEvent;
import brightlogic.globapay.event.FraudDetectionEvent;
import brightlogic.globapay.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditLogEventListener {

    @EventListener
    public void handleAuditLogCreated(AuditLogCreatedEvent event) {
        log.info(" AuditLogCreatedEvent handled: {}", event.getAuditLog());
    }

    @EventListener
    public void handleFraudDetectionAudit(FraudDetectionAuditEvent event) {
        log.info(" FraudDetectionAuditEvent handled: FraudId={}, Reason={}, Flagged={}",
                event.getFraudId(), event.getReason(), event.isFlagged());
    }
}
