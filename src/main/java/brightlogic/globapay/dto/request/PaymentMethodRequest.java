package brightlogic.globapay.dto.request;

import brightlogic.globapay.domain.enums.PaymentMethodType;

public class PaymentMethodRequest {

    private PaymentMethodType type;
    private String provider;
    private String maskedDetails;
    private String referenceId;

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
