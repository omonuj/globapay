package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.PaymentTransactionRequest;
import brightlogic.globapay.dto.response.PaymentTransactionResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentTransactionResponse createPayment(PaymentTransactionRequest request);

    PaymentTransactionResponse processPayment(UUID transactionId);

    PaymentTransactionResponse getPaymentStatus(UUID transactionId);

    List<PaymentTransactionResponse> getPaymentHistory(UUID userId);

    PaymentTransactionResponse refundPayment(UUID transactionId);
}
