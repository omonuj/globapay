package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;

import java.util.List;
import java.util.UUID;

public interface AuditLogService {

    AuditLogResponse createAuditLog(AuditLogRequest request);
    List<AuditLogResponse> getLogsByTransactionId(UUID transactionId);

}
