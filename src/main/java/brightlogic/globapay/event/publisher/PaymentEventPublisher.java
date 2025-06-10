package brightlogic.globapay.event.publisher;


import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.event.PaymentCompletedEvent;
import brightlogic.globapay.event.PaymentFailedEvent;
import brightlogic.globapay.event.PaymentRefundedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEventPublisher;


@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishPaymentCompletedEvent(PaymentTransaction transaction) {
        publisher.publishEvent(new PaymentCompletedEvent(this, transaction));
    }

    public void publishPaymentFailedEvent(PaymentTransaction transaction) {
        publisher.publishEvent(new PaymentFailedEvent(this, transaction));
    }

    public void publishPaymentRefundedEvent(PaymentTransaction transaction) {
        publisher.publishEvent(new PaymentRefundedEvent(this, transaction));
    }
}
