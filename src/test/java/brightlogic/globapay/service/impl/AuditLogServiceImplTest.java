package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.AuditEventType;
import brightlogic.globapay.domain.model.AuditLog;
import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;
import brightlogic.globapay.event.AuditLogCreatedEvent;
import brightlogic.globapay.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditLogServiceImplTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    @Captor
    private ArgumentCaptor<AuditLogCreatedEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAuditLog_shouldSaveLogAndPublishEvent() {

        AuditLogRequest request = new AuditLogRequest();
        request.setUserId(UUID.randomUUID());
        request.setTransactionId(UUID.randomUUID());
        request.setEventType(AuditEventType.PAYMENT_CREATED);
        request.setMessage("Payment started");
        request.setIpAddress("127.0.0.1");
        request.setUserAgent("JUnit");

        AuditLog savedEntity = new AuditLog();
        savedEntity.setAuditId(UUID.randomUUID());
        savedEntity.setUserId(request.getUserId());
        savedEntity.setTransactionId(request.getTransactionId());
        savedEntity.setEventType(request.getEventType());
        savedEntity.setMessage(request.getMessage());
        savedEntity.setIpAddress(request.getIpAddress());
        savedEntity.setUserAgent(request.getUserAgent());
        savedEntity.setTimestamp(Instant.now());

        when(auditLogRepository.save(any())).thenReturn(savedEntity);

        AuditLogResponse response = auditLogService.createAuditLog(request);

        assertNotNull(response);
        assertEquals(request.getUserId(), response.getUserId());
        assertEquals(request.getTransactionId(), response.getTransactionId());
        assertEquals(request.getEventType(), response.getEventType());

        verify(auditLogRepository).save(any());
        verify(eventPublisher).publishEvent(any(AuditLogCreatedEvent.class));
    }

    @Test
    void getLogsByTransactionId_shouldReturnListOfResponses() {
        UUID transactionId = UUID.randomUUID();

        AuditLog log = new AuditLog();
        log.setAuditId(UUID.randomUUID());
        log.setTransactionId(transactionId);
        log.setUserId(UUID.randomUUID());
        log.setEventType(AuditEventType.PAYMENT_COMPLETED);
        log.setMessage("Completed");
        log.setTimestamp(Instant.now());

        when(auditLogRepository.findByTransactionId(transactionId)).thenReturn(List.of(log));

        List<AuditLogResponse> results = auditLogService.getLogsByTransactionId(transactionId);

        assertEquals(1, results.size());
        assertEquals(transactionId, results.get(0).getTransactionId());
    }

    @Test
    void logFraudDetectionEvent_shouldLogAndPublishEvent() {
        UUID fraudId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        String reason = "Suspicious IP";
        boolean flagged = true;

        auditLogService.logFraudDetectionEvent(fraudId, userId, transactionId, reason, flagged);

        verify(eventPublisher).publishEvent(eventCaptor.capture());

        AuditLogCreatedEvent publishedEvent = eventCaptor.getValue();
        assertNotNull(publishedEvent);
        assertEquals(fraudId, publishedEvent.getAuditLog().getTransactionId()); // or whatever your logic maps it to
    }
}
