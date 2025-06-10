package brightlogic.globapay.event;

import brightlogic.globapay.domain.model.PaymentTransaction;

import org.springframework.context.ApplicationEvent;

public class PaymentCompletedEvent extends ApplicationEvent {
    private final PaymentTransaction paymentTransaction;

    public PaymentCompletedEvent(Object source, PaymentTransaction transaction) {
        super(source);
        this.paymentTransaction = transaction;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }
}
