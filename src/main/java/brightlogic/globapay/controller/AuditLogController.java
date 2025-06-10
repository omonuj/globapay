package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.AuditLogRequest;
import brightlogic.globapay.dto.response.AuditLogResponse;
import brightlogic.globapay.service.interfaces.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Log Controller", description = "Manages audit logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping("/create")
    @Operation(summary = "Create an audit log", description = "Logs an event in the system.")
    public ResponseEntity<AuditLogResponse> createAuditLog(@RequestBody AuditLogRequest request) {
        AuditLogResponse response = auditLogService.createAuditLog(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get audit logs by transaction ID", description = "Retrieves all audit logs linked to a transaction.")
    public ResponseEntity<List<AuditLogResponse>> getLogsByTransactionId(@PathVariable UUID transactionId) {
        List<AuditLogResponse> responses = auditLogService.getLogsByTransactionId(transactionId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/fraud")
    @Operation(summary = "Log a fraud detection event", description = "Records a fraud-related event.")
    public ResponseEntity<String> logFraudDetectionEvent(
            @RequestParam UUID fraudId,
            @RequestParam UUID userId,
            @RequestParam UUID transactionId,
            @RequestParam String reason,
            @RequestParam boolean flagged) {

        auditLogService.logFraudDetectionEvent(fraudId, userId, transactionId, reason, flagged);
        return ResponseEntity.ok("Fraud detection event logged successfully.");
    }

}
