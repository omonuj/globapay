package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import brightlogic.globapay.service.interfaces.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fraud")
@RequiredArgsConstructor
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;

    @PostMapping("/detect")
    public ResponseEntity<FraudDetectionResponse> createDetection(@RequestBody FraudDetectionRequest request) {
        return new ResponseEntity<>(fraudDetectionService.createDetection(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FraudDetectionResponse>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(fraudDetectionService.getByUserId(userId));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<FraudDetectionResponse>> getByTransactionId(@PathVariable UUID transactionId) {
        return ResponseEntity.ok(fraudDetectionService.getByTransactionId(transactionId));
    }
}
