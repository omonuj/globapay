package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.repository.ApiKeyRepository;
import brightlogic.globapay.service.interfaces.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    @Override
    public Optional<ApiKey> findByKey(String key) {
        return apiKeyRepository.findByKey(key);
    }

    @Override
    public boolean isValid(String key) {
        return apiKeyRepository.findByKey(key)
                .map(apiKey -> apiKey.isActive() && apiKey.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Override
    public ApiKey save(ApiKey apiKey) {
        apiKey.setApiKeyId(UUID.randomUUID());
        apiKey.setKey(generateApiKey());
        apiKey.setCreatedAt(LocalDateTime.now());
        return apiKeyRepository.save(apiKey);
    }

    private String generateApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
