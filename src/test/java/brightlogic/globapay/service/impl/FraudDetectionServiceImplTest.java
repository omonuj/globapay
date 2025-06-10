package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.FraudDetectionRecord;
import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import brightlogic.globapay.event.FraudDetectionEvent;
import brightlogic.globapay.mapper.FraudDetectionMapper;
import brightlogic.globapay.repository.FraudDetectionRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FraudDetectionServiceImplTest {

    @Mock
    private FraudDetectionRecordRepository repository;

    @Mock
    private FraudDetectionMapper mapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private FraudDetectionServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDetection_publishesEventAndReturnsResponse() {

        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        UUID fraudId = UUID.randomUUID();

        FraudDetectionRequest request = new FraudDetectionRequest();
        request.setUserId(userId);
        request.setTransactionId(transactionId);
        request.setReason("Suspicious IP");
        request.setFlagged(true);

        FraudDetectionRecord recordToSave = new FraudDetectionRecord();
        FraudDetectionRecord savedRecord = new FraudDetectionRecord();
        savedRecord.setFraudId(fraudId);
        savedRecord.setUserId(userId);
        savedRecord.setTransactionId(transactionId);
        savedRecord.setReason("Suspicious IP");
        savedRecord.setFlagged(true);
        savedRecord.setDetectedAt(LocalDateTime.now());

        FraudDetectionResponse response = new FraudDetectionResponse();
        response.setId(fraudId);
        response.setUserId(userId);
        response.setTransactionId(transactionId);
        response.setReason("Suspicious IP");
        response.setFlagged(true);
        response.setDetectedAt(savedRecord.getDetectedAt());

        when(mapper.toEntity(request)).thenReturn(recordToSave);
        when(repository.save(recordToSave)).thenReturn(savedRecord);
        when(mapper.toResponse(savedRecord)).thenReturn(response);

        FraudDetectionResponse result = service.createDetection(request);

        assertEquals(fraudId, result.getId());
        assertEquals("Suspicious IP", result.getReason());
        assertTrue(result.isFlagged());

        verify(eventPublisher, times(1)).publishEvent(any(FraudDetectionEvent.class));
    }

    @Test
    void testGetByUserId_returnsMappedResponses() {

        UUID userId = UUID.randomUUID();
        FraudDetectionRecord record = new FraudDetectionRecord();
        FraudDetectionResponse response = new FraudDetectionResponse();

        when(repository.findByUserId(userId)).thenReturn(Collections.singletonList(record));
        when(mapper.toResponse(record)).thenReturn(response);

        List<FraudDetectionResponse> results = service.getByUserId(userId);

        assertEquals(1, results.size());
        verify(repository).findByUserId(userId);
    }

    @Test
    void testGetByTransactionId_returnsMappedResponses() {

        UUID transactionId = UUID.randomUUID();
        FraudDetectionRecord record = new FraudDetectionRecord();
        FraudDetectionResponse response = new FraudDetectionResponse();

        when(repository.findByTransactionId(transactionId)).thenReturn(Collections.singletonList(record));
        when(mapper.toResponse(record)).thenReturn(response);

        List<FraudDetectionResponse> results = service.getByTransactionId(transactionId);

        assertEquals(1, results.size());
        verify(repository).findByTransactionId(transactionId);
    }
}
