package brightlogic.globapay.event;

import brightlogic.globapay.domain.model.AuditLog;
import org.springframework.context.ApplicationEvent;

public class AuditLogCreatedEvent extends ApplicationEvent {

    private final AuditLog auditLog;

    public AuditLogCreatedEvent(Object source, AuditLog auditLog) {
        super(source);
        this.auditLog = auditLog;
    }

    public AuditLog getAuditLog() {
        return auditLog;
    }
}
