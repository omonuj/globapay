package brightlogic.globapay.controller;

import brightlogic.globapay.dto.response.RateLimitLogResponse;
import brightlogic.globapay.service.interfaces.RateLimitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rate-limits")
@RequiredArgsConstructor
public class RateLimitLogController {

    private final RateLimitLogService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RateLimitLogResponse>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/ip/{ip}")
    public ResponseEntity<List<RateLimitLogResponse>> getByIpAddress(@PathVariable String ip) {
        return ResponseEntity.ok(service.getByIpAddress(ip));
    }

    @GetMapping
    public ResponseEntity<List<RateLimitLogResponse>> getAll() {
        return ResponseEntity.ok(service.getAllLogs());
    }
}
