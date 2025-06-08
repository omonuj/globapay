package brightlogic.globapay.dto.response;

import brightlogic.globapay.domain.enums.AuditEventType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLogResponse {

    private UUID auditId;
    private UUID userId;
    private UUID transactionId;
    private AuditEventType eventType;
    private String message;
    private Instant timestamp;
    private String ipAddress;
    private String userAgent;

    public void setAuditId(UUID auditId) {
        this.auditId = auditId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setEventType(AuditEventType eventType) {
        this.eventType = eventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public AuditEventType getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


}
