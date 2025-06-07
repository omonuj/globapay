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
}
