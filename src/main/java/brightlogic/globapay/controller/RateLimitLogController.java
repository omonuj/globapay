package brightlogic.globapay.controller;

import brightlogic.globapay.dto.response.RateLimitLogResponse;
import brightlogic.globapay.service.interfaces.RateLimitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rate-limits")
@RequiredArgsConstructor
@Tag(name = "Rate Limit Log Controller", description = "Handles rate limit logging operations")
public class RateLimitLogController {

    private final RateLimitLogService service;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get rate limit logs by user ID", description = "Retrieves rate limit logs associated with a specific user")
    public ResponseEntity<List<RateLimitLogResponse>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/ip/{ip}")
    @Operation(summary = "Get rate limit logs by IP address", description = "Retrieves rate limit logs associated with a specific IP address")
    public ResponseEntity<List<RateLimitLogResponse>> getByIpAddress(@PathVariable String ip) {
        return ResponseEntity.ok(service.getByIpAddress(ip));
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieve all rate limit logs", description = "Fetches all logged rate limit events")
    public ResponseEntity<List<RateLimitLogResponse>> getAll() {
        return ResponseEntity.ok(service.getAllLogs());
    }
}