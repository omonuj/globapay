package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.AuditLog;
import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;
import brightlogic.globapay.event.AuditLogCreatedEvent;
import brightlogic.globapay.event.FraudDetectionAuditEvent;
import brightlogic.globapay.exception.auditlogexception.InvalidAuditLogRequestException;
import brightlogic.globapay.mapper.AuditLogMapper;
import brightlogic.globapay.repository.AuditLogRepository;
import brightlogic.globapay.service.interfaces.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public AuditLogResponse createAuditLog(AuditLogRequest request) {
        if (request == null || request.getUserId() == null || request.getTransactionId() == null || request.getEventType() == null) {
            throw new InvalidAuditLogRequestException("Missing required fields in audit log request.");
        }

        AuditLog log = auditLogMapper.toEntity(request);
        AuditLog saved = auditLogRepository.save(log);

        eventPublisher.publishEvent(new AuditLogCreatedEvent(this, saved));

        return auditLogMapper.toResponse(saved);
    }

    @Override
    public List<AuditLogResponse> getLogsByTransactionId(UUID transactionId) {
        return auditLogRepository.findByTransactionId(transactionId)
                .stream().map(auditLogMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void logFraudDetectionEvent(UUID fraudId, UUID userId, UUID transactionId, String reason, boolean flagged) {
        log.info("AUDIT LOG: Fraud event logged - Fraud ID: {}, User ID: {}, Transaction ID: {}, Reason: {}, Flagged: {}",
                fraudId, userId, transactionId, reason, flagged);

        eventPublisher.publishEvent(new FraudDetectionAuditEvent(this, fraudId, userId, transactionId, reason, flagged));
    }

}
