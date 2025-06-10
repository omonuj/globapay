package brightlogic.globapay.service.interfaces;
import brightlogic.globapay.domain.model.ApiKey;

import java.util.Optional;

public interface ApiKeyService {

    Optional<ApiKey> findByKey(String key);
    boolean isValid(String key);

    ApiKey save(ApiKey apiKey);
}
