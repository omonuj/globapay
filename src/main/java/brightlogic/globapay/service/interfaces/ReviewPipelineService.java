package brightlogic.globapay.service.interfaces;

import java.util.UUID;

public interface ReviewPipelineService {
    void triggerReview(UUID fraudId, UUID userId, UUID transactionId, String reason, boolean flagged);

}
