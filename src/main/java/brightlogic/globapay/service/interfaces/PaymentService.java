package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.CreatePaymentRequest;
import brightlogic.globapay.dto.response.PaymentHistoryResponse;
import brightlogic.globapay.dto.response.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse processPayment(UUID transactionId);

    PaymentResponse getPaymentStatus(UUID transactionId);

    List<PaymentHistoryResponse> getPaymentHistory(UUID userId);

    PaymentResponse refundPayment(UUID transactionId);
}
