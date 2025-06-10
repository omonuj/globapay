package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.RateLimitLog;
import brightlogic.globapay.dto.request.RateLimitLogRequest;
import brightlogic.globapay.dto.response.RateLimitLogResponse;
import brightlogic.globapay.repository.RateLimitLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimitLogServiceImplTest {

    private RateLimitLogRepository repository;
    private RateLimitLogServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(RateLimitLogRepository.class);
        service = new RateLimitLogServiceImpl(repository);
    }

    @Test
    void logRequest() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        RateLimitLogRequest request = new RateLimitLogRequest();
        request.setUserId(userId);
        request.setIpAddress("192.168.1.1");
        request.setEndpoint("/api/test");
        request.setRequestCount(5);
        request.setWindowStart(now.minusMinutes(1));
        request.setWindowEnd(now);

        RateLimitLog savedLog = new RateLimitLog();
        savedLog.setRateId(UUID.randomUUID());
        savedLog.setUserId(userId);
        savedLog.setIpAddress(request.getIpAddress());
        savedLog.setEndpoint(request.getEndpoint());
        savedLog.setRequestCount(request.getRequestCount());
        savedLog.setWindowStart(request.getWindowStart());
        savedLog.setWindowEnd(request.getWindowEnd());

        when(repository.save(any(RateLimitLog.class))).thenReturn(savedLog);

        RateLimitLogResponse response = service.logRequest(request);

        assertNotNull(response);
        assertEquals(request.getUserId(), response.getUserId());
        assertEquals(request.getIpAddress(), response.getIpAddress());
        assertEquals(request.getEndpoint(), response.getEndpoint());
        assertEquals(request.getRequestCount(), response.getRequestCount());

        verify(repository, times(1)).save(any(RateLimitLog.class));
    }

    @Test
    void getByUserId() {
        UUID userId = UUID.randomUUID();

        RateLimitLog log1 = new RateLimitLog();
        log1.setRateId(UUID.randomUUID());
        log1.setUserId(userId);
        log1.setIpAddress("192.168.0.1");

        RateLimitLog log2 = new RateLimitLog();
        log2.setRateId(UUID.randomUUID());
        log2.setUserId(userId);
        log2.setIpAddress("192.168.0.2");

        when(repository.findByUserId(userId)).thenReturn(List.of(log1, log2));

        List<RateLimitLogResponse> responses = service.getByUserId(userId);

        assertEquals(2, responses.size());
        assertEquals(userId, responses.get(0).getUserId());
        verify(repository).findByUserId(userId);
    }

    @Test
    void getByIpAddress() {
        String ip = "192.168.100.100";

        RateLimitLog log = new RateLimitLog();
        log.setRateId(UUID.randomUUID());
        log.setUserId(UUID.randomUUID());
        log.setIpAddress(ip);

        when(repository.findByIpAddress(ip)).thenReturn(List.of(log));

        List<RateLimitLogResponse> responses = service.getByIpAddress(ip);

        assertEquals(1, responses.size());
        assertEquals(ip, responses.get(0).getIpAddress());
        verify(repository).findByIpAddress(ip);
    }

    @Test
    void getAllLogs() {
        RateLimitLog log1 = new RateLimitLog();
        log1.setRateId(UUID.randomUUID());

        RateLimitLog log2 = new RateLimitLog();
        log2.setRateId(UUID.randomUUID());

        when(repository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<RateLimitLogResponse> responses = service.getAllLogs();

        assertEquals(2, responses.size());
        verify(repository).findAll();
    }
}
