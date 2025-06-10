package brightlogic.globapay.controller;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.dto.request.ApiKeyRequest;
import brightlogic.globapay.dto.response.ApiKeyResponse;
import brightlogic.globapay.exception.apikeyexception.ApiKeyNotFoundException;
import brightlogic.globapay.mapper.ApiKeyMapper;
import brightlogic.globapay.service.interfaces.ApiKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/apikey")
@RequiredArgsConstructor
@Tag(name = "API Key Controller", description = "Handles API key management")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;
    private final ApiKeyMapper apiKeyMapper;

    @PostMapping
    @Operation(summary = "Create a new API key", description = "Generates and saves a new API key")
    public ResponseEntity<ApiKeyResponse> createKey(@RequestBody ApiKeyRequest request) {
        ApiKey apiKey = apiKeyMapper.toEntity(request);
        ApiKey saved = apiKeyService.save(apiKey);
        return ResponseEntity.ok(apiKeyMapper.toResponse(saved));
    }

    @GetMapping("/{key}")
    @Operation(summary = "Retrieve an API key by its value", description = "Finds an API key using the provided key string")
    public ResponseEntity<ApiKeyResponse> getByKey(@PathVariable String key) {
        Optional<ApiKey> result = apiKeyService.findByKey(key);
        return result.map(apiKeyMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiKeyNotFoundException("Key not found: " + key));
    }
}