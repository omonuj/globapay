package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.RateLimitLog;
import brightlogic.globapay.dto.request.RateLimitLogRequest;
import brightlogic.globapay.dto.response.RateLimitLogResponse;
import brightlogic.globapay.repository.RateLimitLogRepository;
import brightlogic.globapay.service.interfaces.RateLimitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateLimitLogServiceImpl implements RateLimitLogService {


    private final RateLimitLogRepository repository;

    @Override
    public RateLimitLogResponse logRequest(RateLimitLogRequest request) {
        RateLimitLog log = new RateLimitLog();
        log.setUserId(request.getUserId());
        log.setIpAddress(request.getIpAddress());
        log.setEndpoint(request.getEndpoint());
        log.setRequestCount(request.getRequestCount());
        log.setWindowStart(request.getWindowStart());
        log.setWindowEnd(request.getWindowEnd());

        RateLimitLog saved = repository.save(log);
        return toResponse(saved);
    }

    @Override
    public List<RateLimitLogResponse> getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RateLimitLogResponse> getByIpAddress(String ipAddress) {
        return repository.findByIpAddress(ipAddress)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<RateLimitLogResponse> getAllLogs() {
        List<RateLimitLog> logs = repository.findAll();
        return logs.stream()
                .map(this::toResponse)
                .toList();
    }

    private RateLimitLogResponse toResponse(RateLimitLog log) {
        RateLimitLogResponse res = new RateLimitLogResponse();
        res.setRateId(log.getRateId());
        res.setUserId(log.getUserId());
        res.setIpAddress(log.getIpAddress());
        res.setEndpoint(log.getEndpoint());
        res.setRequestCount(log.getRequestCount());
        res.setWindowStart(log.getWindowStart());
        res.setWindowEnd(log.getWindowEnd());
        return res;
    }
}
