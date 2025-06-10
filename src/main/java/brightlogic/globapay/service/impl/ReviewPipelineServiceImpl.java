package brightlogic.globapay.service.impl;

import brightlogic.globapay.service.interfaces.ReviewPipelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReviewPipelineServiceImpl implements ReviewPipelineService {
    @Override
    public void triggerReview(UUID fraudId, UUID userId, UUID transactionId, String reason, boolean flagged) {
        log.info("🔁 REVIEW PIPELINE: Triggered review for Fraud ID: {}, User ID: {}, Transaction ID: {}, Reason: {}, Flagged: {}",
                fraudId, userId, transactionId, reason, flagged);
    }
}
