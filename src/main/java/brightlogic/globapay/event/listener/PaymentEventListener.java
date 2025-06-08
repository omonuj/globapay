package brightlogic.globapay.event.listener;

import brightlogic.globapay.event.PaymentCompletedEvent;
import brightlogic.globapay.event.PaymentFailedEvent;
import brightlogic.globapay.event.PaymentRefundedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventListener {


    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Payment COMPLETED: {}", event.getPaymentTransaction().getTransactionId());
    }

    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.warn("Payment FAILED: {}", event.getPaymentTransaction().getTransactionId());

    }

    @EventListener
    public void handlePaymentRefunded(PaymentRefundedEvent event) {
        log.info("Payment REFUNDED: {}", event.getPaymentTransaction().getTransactionId());

    }
}
