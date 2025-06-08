package brightlogic.globapay.dto.request;

import brightlogic.globapay.domain.enums.AuditEventType;

import java.util.UUID;

public class AuditLogRequest {

    private UUID userId;
    private UUID transactionId;
    private AuditEventType eventType;
    private String message;
    private String ipAddress;
    private String userAgent;

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

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
