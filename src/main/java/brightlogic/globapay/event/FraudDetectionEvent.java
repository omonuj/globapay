package brightlogic.globapay.event;

import java.util.UUID;

public class FraudDetectionEvent {

    private final UUID fraudId;
    private final UUID userId;
    private final UUID transactionId;
    private final String reason;
    private final boolean flagged;

    public FraudDetectionEvent(UUID fraudId, UUID userId, UUID transactionId, String reason, boolean flagged) {
        this.fraudId = fraudId;
        this.userId = userId;
        this.transactionId = transactionId;
        this.reason = reason;
        this.flagged = flagged;
    }

    public UUID getFraudId() {
        return fraudId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getReason() {
        return reason;
    }

    public boolean isFlagged() {
        return flagged;
    }
}
