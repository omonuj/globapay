package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.domain.model.PaymentMethod;

public interface PaymentMethodService {

    PaymentMethod process(PaymentMethod method);
}
