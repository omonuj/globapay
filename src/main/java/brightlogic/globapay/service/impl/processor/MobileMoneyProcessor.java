package brightlogic.globapay.service.impl.processor;

import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.service.interfaces.processor.PaymentProcessor;
import org.springframework.stereotype.Component;

@Component
public class MobileMoneyProcessor implements PaymentProcessor {

    @Override
    public boolean process(PaymentTransaction transaction) {
        // Simulate mobile money processing
        return true;
    }

    @Override
    public boolean refund(PaymentTransaction transaction) {
        return true;
    }
}
