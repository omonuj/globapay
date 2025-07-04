package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import brightlogic.globapay.service.interfaces.FraudDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fraud")
@RequiredArgsConstructor
@Tag(name = "Fraud Detection Controller", description = "Manages fraud detection operations")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;

    @PostMapping("/detect")
    @Operation(summary = "Detect fraud", description = "Creates a new fraud detection log based on request data")
    public ResponseEntity<FraudDetectionResponse> createDetection(@RequestBody FraudDetectionRequest request) {
        return new ResponseEntity<>(fraudDetectionService.createDetection(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get fraud logs by user ID", description = "Retrieves all fraud detection logs for a specific user")
    public ResponseEntity<List<FraudDetectionResponse>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(fraudDetectionService.getByUserId(userId));
    }

    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get fraud logs by transaction ID", description = "Retrieves all fraud detection logs linked to a transaction")
    public ResponseEntity<List<FraudDetectionResponse>> getByTransactionId(@PathVariable UUID transactionId) {
        return ResponseEntity.ok(fraudDetectionService.getByTransactionId(transactionId));
    }
}