package brightlogic.globapay.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity(name = "api_key")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID apiKeyId;

    private String clientName;

    private String key;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    public ApiKey(UUID apiKeyId, String clientName, String key,
                  boolean active, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.apiKeyId = apiKeyId;
        this.clientName = clientName;
        this.key = key;
        this.active = active;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public ApiKey(UUID apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public ApiKey() {
    }

    public UUID getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(UUID apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
