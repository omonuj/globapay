package brightlogic.globapay.service.interfaces.processor;

import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.model.PaymentTransaction;

public interface PaymentProcessor {

    boolean process(PaymentTransaction transaction);
    boolean refund(PaymentTransaction transaction);
}
