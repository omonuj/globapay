package brightlogic.globapay.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID exchangeRateId;

    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal rate;


    private boolean isActive;

    public ExchangeRate(UUID exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public ExchangeRate(UUID exchangeRateId, String sourceCurrency, String targetCurrency,
                        BigDecimal rate, boolean isActive) {
        this.exchangeRateId = exchangeRateId;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.isActive = isActive;
    }

    public ExchangeRate() {
    }

    public UUID getExchangeRateId() {
        return exchangeRateId;
    }

    public void setId(UUID exchangeRateId) {
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

    public void setActive(boolean active) {
        isActive = active;
    }


}
