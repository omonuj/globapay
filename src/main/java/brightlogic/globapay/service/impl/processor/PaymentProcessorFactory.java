package brightlogic.globapay.service.impl.processor;

import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.exception.UnsupportedPaymentMethodException;
import brightlogic.globapay.service.interfaces.processor.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static brightlogic.globapay.domain.enums.PaymentMethodType.*;

@RequiredArgsConstructor
@Component
public class PaymentProcessorFactory {


    private final CardPaymentProcessor cardProcessor;
    private final BankTransferProcessor bankProcessor;
    private final MobileMoneyProcessor mobileMoneyProcessor;


    public PaymentProcessor getProcessor(PaymentMethodType paymentMethodType) {
        if (paymentMethodType == null) {
            throw new UnsupportedPaymentMethodException("Payment method is not specified");
        }

        return switch (paymentMethodType) {
            case CARD -> new CardPaymentProcessor();
            case BANK_TRANSFER -> new BankTransferProcessor();
            default -> throw new UnsupportedPaymentMethodException("Unsupported payment method: " + paymentMethodType);
        };
    }


}
