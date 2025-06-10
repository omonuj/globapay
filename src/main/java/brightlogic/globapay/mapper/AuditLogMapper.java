package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.model.AuditLog;
import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    AuditLog toEntity(AuditLogRequest request);
    AuditLogResponse toResponse(AuditLog log);
}
