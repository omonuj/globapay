package brightlogic.globapay.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity(name = "rate_limit_log")
public class RateLimitLog {

    @Id
    @GeneratedValue
    private UUID rateId;

    private UUID userId;

    private String ipAddress;

    private String endpoint;

    private int requestCount;

    private LocalDateTime windowStart;

    private LocalDateTime windowEnd;

    public RateLimitLog(UUID rateId, UUID userId, String ipAddress, String endpoint,
                        int requestCount, LocalDateTime windowStart, LocalDateTime windowEnd) {
        this.rateId = rateId;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.endpoint = endpoint;
        this.requestCount = requestCount;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
    }

    public RateLimitLog(UUID rateId) {
        this.rateId = rateId;
    }

    public RateLimitLog() {
    }

    public LocalDateTime getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(LocalDateTime windowStart) {
        this.windowStart = windowStart;
    }

    public UUID getRateId() {
        return rateId;
    }

    public void setRateId(UUID rateId) {
        this.rateId = rateId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public LocalDateTime getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(LocalDateTime windowEnd) {
        this.windowEnd = windowEnd;
    }
}
