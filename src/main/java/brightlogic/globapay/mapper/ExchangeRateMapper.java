package brightlogic.globapay.mapper;


import brightlogic.globapay.domain.model.ExchangeRate;
import brightlogic.globapay.dto.request.ExchangeRateRequest;
import brightlogic.globapay.dto.response.ExchangeRateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    ExchangeRate toEntity(ExchangeRateRequest request);
    ExchangeRateResponse toResponse(ExchangeRate rate);
}
