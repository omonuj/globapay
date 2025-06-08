package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.dto.request.CreatePaymentRequest;
import brightlogic.globapay.dto.response.PaymentHistoryResponse;
import brightlogic.globapay.dto.response.PaymentResponse;
import brightlogic.globapay.mapper.PaymentMapper;
import brightlogic.globapay.repository.PaymentTransactionRepository;
import brightlogic.globapay.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentProcessorFactory paymentProcessorFactory;
    private final CurrencyConversionService currencyConversionService;
    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        // Validate currency supported
        currencyConversionService.validateCurrency(request.getCurrency());

        // Get exchange rate and convert to base currency (e.g., USD)
        var rate = currencyConversionService.getExchangeRate(request.getCurrency(), CurrencyType.USD);
        double amountInBase = request.getAmount() * rate;

        var transaction = PaymentTransaction.builder()
                .transactionId(UUID.randomUUID())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .exchangeRate(rate)
                .amountInBaseCurrency(amountInBase)
                .build();

        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        return paymentMapper.toPaymentResponse(saved);
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentException("Transaction not found"));

        if (transaction.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentException("Transaction cannot be processed in current status");
        }

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(transaction.getPaymentMethod());

        boolean success = processor.process(transaction);

        transaction.setStatus(success ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        transaction.setUpdatedAt(Instant.now());
        paymentTransactionRepository.save(transaction);

        // Publish payment event
        if(success) {
            paymentEventPublisher.publishPaymentCompletedEvent(transaction);
        } else {
            paymentEventPublisher.publishPaymentFailedEvent(transaction);
        }

        return paymentMapper.toPaymentResponse(transaction);
    }

    @Override
    public PaymentResponse getPaymentStatus(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentException("Transaction not found"));
        return paymentMapper.toPaymentResponse(transaction);
    }

    @Override
    public List<PaymentResponse> getPaymentHistory(UUID userId) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponse refundPayment(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentException("Transaction not found"));

        if (transaction.getStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentException("Only COMPLETED transactions can be refunded");
        }

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(transaction.getPaymentMethod());

        boolean refunded = processor.refund(transaction);

        if (refunded) {
            transaction.setStatus(PaymentStatus.REFUNDED);
            transaction.setUpdatedAt(Instant.now());
            paymentTransactionRepository.save(transaction);
            paymentEventPublisher.publishPaymentRefundedEvent(transaction);
        }

        return paymentMapper.toPaymentResponse(transaction);
    }

}
