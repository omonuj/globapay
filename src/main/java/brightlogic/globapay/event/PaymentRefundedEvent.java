package brightlogic.globapay.event;

import brightlogic.globapay.domain.model.PaymentTransaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentRefundedEvent extends ApplicationEvent {

    private final PaymentTransaction paymentTransaction;

    public PaymentRefundedEvent(Object source, PaymentTransaction transaction) {
        super(source);
        this.paymentTransaction = transaction;
    }
}
