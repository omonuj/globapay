package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.PaymentMethod;
import brightlogic.globapay.service.interfaces.PaymentMethodService;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Override
    public PaymentMethod process(PaymentMethod method) {
        return method;
    }
}
