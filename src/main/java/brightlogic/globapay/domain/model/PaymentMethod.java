package brightlogic.globapay.domain.model;

import brightlogic.globapay.domain.enums.PaymentMethodType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;

@Embeddable
public class PaymentMethod implements Serializable {

    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    private String provider; // e.g., "Visa", "GTBank", "PayPal"

    private String maskedDetails; // e.g., ****1234

    private String referenceId;

    public PaymentMethod(PaymentMethodType type, String provider,
                         String maskedDetails, String referenceId) {
        this.type = type;
        this.provider = provider;
        this.maskedDetails = maskedDetails;
        this.referenceId = referenceId;
    }

    public PaymentMethod(PaymentMethodType type) {
        this.type = type;
    }

    public PaymentMethod() {
    }

    public PaymentMethodType getType() {
        return type;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMaskedDetails() {
        return maskedDetails;
    }

    public void setMaskedDetails(String maskedDetails) {
        this.maskedDetails = maskedDetails;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
