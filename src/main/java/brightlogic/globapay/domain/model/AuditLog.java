package brightlogic.globapay.domain.model;

import brightlogic.globapay.domain.enums.AuditEventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditEventType eventType;

    @Column(length = 1000)
    private String message;

    private UUID userId;

    private UUID transactionId;

    @CreationTimestamp
    private Instant timestamp;

    private String ipAddress;

    private String userAgent;
}
