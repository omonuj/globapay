package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.AuditLog;
import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;
import brightlogic.globapay.repository.AuditLogRepository;
import brightlogic.globapay.service.interfaces.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public AuditLogResponse createAuditLog(AuditLogRequest request) {
        AuditLog log = new AuditLog();
        log.setUserId(request.getUserId());
        log.setTransactionId(request.getTransactionId());
        log.setEventType(request.getEventType());
        log.setMessage(request.getMessage());
        log.setIpAddress(request.getIpAddress());
        log.setUserAgent(request.getUserAgent());

        AuditLog saved = auditLogRepository.save(log);
        return toResponse(saved);
    }

    @Override
    public List<AuditLogResponse> getLogsByTransactionId(UUID transactionId) {
        return auditLogRepository.findByTransactionId(transactionId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private AuditLogResponse toResponse(AuditLog log) {
        AuditLogResponse response = new AuditLogResponse();
        response.setAuditId(log.getAuditId());
        response.setUserId(log.getUserId());
        response.setTransactionId(log.getTransactionId());
        response.setEventType(log.getEventType());
        response.setMessage(log.getMessage());
        response.setTimestamp(log.getTimestamp());
        response.setIpAddress(log.getIpAddress());
        response.setUserAgent(log.getUserAgent());
        return response;
    }
}
