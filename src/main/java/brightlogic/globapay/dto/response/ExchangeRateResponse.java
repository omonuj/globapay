package brightlogic.globapay.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class ExchangeRateResponse {

    private UUID exchangeRateId;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private boolean isActive;
    private Timestamp timestamp;

    public UUID getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(UUID exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
