package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.dto.request.ApiKeyRequest;
import brightlogic.globapay.dto.response.ApiKeyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiKeyMapper {
    ApiKey toEntity(ApiKeyRequest request);

    ApiKeyResponse toResponse(ApiKey apiKey);
}
