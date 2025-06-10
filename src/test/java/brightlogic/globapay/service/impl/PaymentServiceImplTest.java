package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.enums.PaymentStatus;
import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.dto.request.PaymentTransactionRequest;
import brightlogic.globapay.dto.response.PaymentTransactionResponse;
import brightlogic.globapay.event.publisher.PaymentEventPublisher;
import brightlogic.globapay.exception.paymentexception.PaymentProcessingException;
import brightlogic.globapay.mapper.PaymentMapper;
import brightlogic.globapay.repository.PaymentTransactionRepository;
import brightlogic.globapay.service.impl.processor.PaymentProcessorFactory;
import brightlogic.globapay.service.interfaces.ExchangeRateService;
import brightlogic.globapay.service.interfaces.processor.PaymentProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentTransactionRepository repository;
    private PaymentMapper mapper;
    private PaymentProcessorFactory processorFactory;
    private ExchangeRateService exchangeRateService;
    private PaymentEventPublisher eventPublisher;

    private PaymentServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(PaymentTransactionRepository.class);
        mapper = mock(PaymentMapper.class);
        processorFactory = mock(PaymentProcessorFactory.class);
        exchangeRateService = mock(ExchangeRateService.class);
        eventPublisher = mock(PaymentEventPublisher.class);

        service = new PaymentServiceImpl(repository, mapper, processorFactory,
                exchangeRateService, eventPublisher);
    }

    @Test
    void createPayment_success() {
        PaymentTransactionRequest request = new PaymentTransactionRequest();
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency(CurrencyType.EUR);
        request.setUserId(UUID.randomUUID());
        request.setPaymentMethod("CARD");

        CurrencyType currencyEnum = CurrencyType.valueOf("EUR");
        doNothing().when(exchangeRateService).validateCurrency(currencyEnum);
        when(exchangeRateService.getExchangeRate(currencyEnum, CurrencyType.USD)).thenReturn(1.1);


        PaymentTransaction entity = new PaymentTransaction();
        PaymentTransaction savedEntity = new PaymentTransaction();
        PaymentTransactionResponse response = new PaymentTransactionResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(any(PaymentTransaction.class))).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        PaymentTransactionResponse result = service.createPayment(request);

        assertEquals(response, result);

        assertEquals(BigDecimal.valueOf(110).setScale(1, BigDecimal.ROUND_HALF_UP),
                entity.getAmountInBaseCurrency());

        verify(exchangeRateService).validateCurrency(CurrencyType.EUR);
        verify(exchangeRateService).getExchangeRate(CurrencyType.EUR, CurrencyType.USD);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(savedEntity);
    }

    @Test
    void createPayment_shouldThrowForInvalidAmount() {
        PaymentTransactionRequest request = new PaymentTransactionRequest();
        request.setAmount(BigDecimal.ZERO);
        request.setCurrency(CurrencyType.USD);
        request.setUserId(UUID.randomUUID());

        assertThrows(IllegalArgumentException.class, () -> service.createPayment(request));
    }

    @Test
    void createPayment_shouldThrowForNullUserId() {
        PaymentTransactionRequest request = new PaymentTransactionRequest();
        request.setAmount(BigDecimal.valueOf(10));
        request.setCurrency(CurrencyType.USD);
        request.setUserId(null);

        assertThrows(IllegalArgumentException.class, () -> service.createPayment(request));
    }

    @Test
    void processPayment_success() {
        UUID transactionId = UUID.randomUUID();

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setPaymentMethod(PaymentMethodType.CARD);

        PaymentProcessor processor = mock(PaymentProcessor.class);
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));
        when(processorFactory.getProcessor(PaymentMethodType.CARD)).thenReturn(processor);
        when(processor.process(transaction)).thenReturn(true);

        PaymentTransactionResponse response = new PaymentTransactionResponse();
        when(mapper.toResponse(transaction)).thenReturn(response);

        PaymentTransactionResponse result = service.processPayment(transactionId);

        assertEquals(PaymentStatus.COMPLETED, transaction.getStatus());
        verify(repository).save(transaction);
        verify(eventPublisher).publishPaymentCompletedEvent(transaction);
        assertEquals(response, result);
    }

    @Test
    void processPayment_failure() {
        UUID transactionId = UUID.randomUUID();

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setPaymentMethod(PaymentMethodType.CARD);

        PaymentProcessor processor = mock(PaymentProcessor.class);
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));
        when(processorFactory.getProcessor(PaymentMethodType.CARD)).thenReturn(processor);
        when(processor.process(transaction)).thenReturn(false);

        PaymentTransactionResponse response = new PaymentTransactionResponse();
        when(mapper.toResponse(transaction)).thenReturn(response);

        PaymentTransactionResponse result = service.processPayment(transactionId);

        assertEquals(PaymentStatus.FAILED, transaction.getStatus());
        verify(repository).save(transaction);
        verify(eventPublisher).publishPaymentFailedEvent(transaction);
        assertEquals(response, result);
    }

    @Test
    void processPayment_shouldThrowIfNotPending() {
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStatus(PaymentStatus.COMPLETED);
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));

        assertThrows(PaymentProcessingException.class, () -> service.processPayment(transactionId));
    }

    @Test
    void processPayment_shouldThrowIfNotFound() {
        UUID transactionId = UUID.randomUUID();
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        assertThrows(PaymentProcessingException.class, () -> service.processPayment(transactionId));
    }

    @Test
    void getPaymentStatus_shouldReturnResponse() {
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = new PaymentTransaction();
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));

        PaymentTransactionResponse response = new PaymentTransactionResponse();
        when(mapper.toResponse(transaction)).thenReturn(response);

        PaymentTransactionResponse result = service.getPaymentStatus(transactionId);

        assertEquals(response, result);
    }

    @Test
    void getPaymentStatus_shouldThrowIfNotFound() {
        UUID transactionId = UUID.randomUUID();
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        assertThrows(PaymentProcessingException.class, () -> service.getPaymentStatus(transactionId));
    }

    @Test
    void getPaymentHistory_shouldReturnList() {
        UUID userId = UUID.randomUUID();

        List<PaymentTransaction> transactions = List.of(new PaymentTransaction(), new PaymentTransaction());
        List<PaymentTransactionResponse> responses = List.of(new PaymentTransactionResponse(), new PaymentTransactionResponse());

        when(repository.findByUserId(userId)).thenReturn(transactions);
        when(mapper.toResponse(transactions.get(0))).thenReturn(responses.get(0));
        when(mapper.toResponse(transactions.get(1))).thenReturn(responses.get(1));

        List<PaymentTransactionResponse> result = service.getPaymentHistory(userId);

        assertEquals(responses, result);
    }

    @Test
    void refundPayment_success() {
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStatus(PaymentStatus.COMPLETED);
        transaction.setPaymentMethod(PaymentMethodType.CARD);

        PaymentProcessor processor = mock(PaymentProcessor.class);
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));
        when(processorFactory.getProcessor(PaymentMethodType.CARD)).thenReturn(processor);
        when(processor.refund(transaction)).thenReturn(true);

        PaymentTransactionResponse response = new PaymentTransactionResponse();
        when(mapper.toResponse(transaction)).thenReturn(response);

        PaymentTransactionResponse result = service.refundPayment(transactionId);

        assertEquals(PaymentStatus.REFUNDED, transaction.getStatus());
        verify(repository).save(transaction);
        verify(eventPublisher).publishPaymentRefundedEvent(transaction);
        assertEquals(response, result);
    }

    @Test
    void refundPayment_shouldThrowIfNotCompleted() {
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStatus(PaymentStatus.FAILED);
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.of(transaction));

        assertThrows(PaymentProcessingException.class, () -> service.refundPayment(transactionId));
    }

    @Test
    void refundPayment_shouldThrowIfNotFound() {
        UUID transactionId = UUID.randomUUID();
        when(repository.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        assertThrows(PaymentProcessingException.class, () -> service.refundPayment(transactionId));
    }
}
