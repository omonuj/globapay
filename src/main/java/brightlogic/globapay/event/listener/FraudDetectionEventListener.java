package brightlogic.globapay.event;

import brightlogic.globapay.service.interfaces.AlertService;
import brightlogic.globapay.service.interfaces.AuditLogService;
import brightlogic.globapay.service.interfaces.ReviewPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FraudDetectionEventListener {

    private final AlertService alertService;
    private final AuditLogService auditLogService;
    private final ReviewPipelineService reviewPipelineService;

    @EventListener
    public void handleFraudDetection(FraudDetectionEvent event) {
        log.info(" Fraud Detection Event Received:");
        log.info("Fraud ID: {}", event.getFraudId());
        log.info("User ID: {}", event.getUserId());
        log.info("Transaction ID: {}", event.getTransactionId());
        log.info("Reason: {}", event.getReason());
        log.info("Flagged: {}", event.isFlagged());

        // ✅ Notify security or monitoring system
        alertService.sendFraudAlert(
                event.getUserId(),
                event.getTransactionId(),
                event.getReason(),
                event.isFlagged()
        );

        // ✅ Log to audit system
        auditLogService.logFraudDetectionEvent(
                event.getFraudId(),
                event.getUserId(),
                event.getTransactionId(),
                event.getReason(),
                event.isFlagged()
        );

        // ✅ Trigger automated review pipeline
        reviewPipelineService.triggerReview(
                event.getFraudId(),
                event.getUserId(),
                event.getTransactionId(),
                event.getReason(),
                event.isFlagged()
        );
    }
}
