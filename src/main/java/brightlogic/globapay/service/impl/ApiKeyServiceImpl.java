package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.repository.ApiKeyRepository;
import brightlogic.globapay.service.interfaces.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return apiKeyRepository.existsByKey(key);
    }
}
