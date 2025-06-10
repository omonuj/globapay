package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentStatus;
import brightlogic.globapay.dto.request.PaymentTransactionRequest;
import brightlogic.globapay.dto.response.PaymentTransactionResponse;
import brightlogic.globapay.event.publisher.PaymentEventPublisher;
import brightlogic.globapay.exception.paymentexception.PaymentProcessingException;
import brightlogic.globapay.mapper.PaymentMapper;
import brightlogic.globapay.service.interfaces.processor.PaymentProcessor;
import brightlogic.globapay.repository.PaymentTransactionRepository;
import brightlogic.globapay.service.impl.processor.PaymentProcessorFactory;
import brightlogic.globapay.service.interfaces.ExchangeRateService;  // changed import
import brightlogic.globapay.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentProcessorFactory paymentProcessorFactory;
    private final ExchangeRateService exchangeRateService;  // changed here
    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    @Transactional
    public PaymentTransactionResponse createPayment(PaymentTransactionRequest request) {

        exchangeRateService.validateCurrency(request.getCurrency());

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }


        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID must be provided");
        }

        double rate = exchangeRateService.getExchangeRate(request.getCurrency(), CurrencyType.USD);
        BigDecimal amountInBase = request.getAmount().multiply(BigDecimal.valueOf(rate));


        var transaction = paymentMapper.toEntity(request);
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setExchangeRate(rate);
        transaction.setAmountInBaseCurrency(amountInBase);
        transaction.setUserId(request.getUserId());

        var saved = paymentTransactionRepository.save(transaction);
        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PaymentTransactionResponse processPayment(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentProcessingException("Transaction not found"));

        if (transaction.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentProcessingException("Transaction cannot be processed in current status");
        }

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(transaction.getPaymentMethod());

        boolean success = processor.process(transaction);

        transaction.setStatus(success ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        transaction.setUpdatedAt(LocalDateTime.now());
        paymentTransactionRepository.save(transaction);

        if (success) {
            paymentEventPublisher.publishPaymentCompletedEvent(transaction);
        } else {
            paymentEventPublisher.publishPaymentFailedEvent(transaction);
        }

        return paymentMapper.toResponse(transaction);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PaymentTransactionResponse getPaymentStatus(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentProcessingException("Transaction not found"));
        return paymentMapper.toResponse(transaction);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PaymentTransactionResponse> getPaymentHistory(UUID userId) {
        return paymentTransactionRepository.findByUserId(userId).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentTransactionResponse refundPayment(UUID transactionId) {
        var transaction = paymentTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentProcessingException("Transaction not found"));

        if (transaction.getStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentProcessingException("Only COMPLETED transactions can be refunded");
        }

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(transaction.getPaymentMethod());

        boolean refunded = processor.refund(transaction);

        if (refunded) {
            transaction.setStatus(PaymentStatus.REFUNDED);
            transaction.setUpdatedAt(LocalDateTime.now());
            paymentTransactionRepository.save(transaction);
            paymentEventPublisher.publishPaymentRefundedEvent(transaction);
        }

        return paymentMapper.toResponse(transaction);
    }
}
