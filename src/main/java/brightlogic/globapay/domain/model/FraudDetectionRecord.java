package brightlogic.globapay.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity(name = "fraud_detection")
public class FraudDetectionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fraudId;

    private UUID transactionId;

    private UUID userId;

    private String reason;

    private double riskScore;

    private boolean flagged;

    private LocalDateTime detectedAt;

    public FraudDetectionRecord(UUID fraudId, UUID transactionId,
                                UUID userId, String reason, double riskScore,
                                boolean flagged, LocalDateTime detectedAt) {
        this.fraudId = fraudId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.reason = reason;
        this.riskScore = riskScore;
        this.flagged = flagged;
        this.detectedAt = detectedAt;
    }

    public FraudDetectionRecord(UUID fraudId) {
        this.fraudId = fraudId;
    }

    public FraudDetectionRecord() {
    }

    public UUID getFraudId() {
        return fraudId;
    }

    public void setFraudId(UUID fraudId) {
        this.fraudId = fraudId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}
