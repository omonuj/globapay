package brightlogic.globapay.controller;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.dto.request.ApiKeyRequest;
import brightlogic.globapay.dto.response.ApiKeyResponse;
import brightlogic.globapay.exception.apikeyexception.ApiKeyNotFoundException;
import brightlogic.globapay.mapper.ApiKeyMapper;
import brightlogic.globapay.service.interfaces.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/apikey")
@RequiredArgsConstructor
public class ApiKeyController {


    private final ApiKeyService apiKeyService;
    private final ApiKeyMapper apiKeyMapper;

    @PostMapping
    public ApiKeyResponse createKey(@RequestBody ApiKeyRequest request) {
        ApiKey apiKey = apiKeyMapper.toEntity(request);
        ApiKey saved = apiKeyService.save(apiKey);
        return apiKeyMapper.toResponse(saved);
    }

    @GetMapping("/{key}")
    public ApiKeyResponse getByKey(@PathVariable String key) {
        Optional<ApiKey> result = apiKeyService.findByKey(key);
        return result.map(apiKeyMapper::toResponse)
                .orElseThrow(() -> new ApiKeyNotFoundException("Key not found: " + key));
    }
}
