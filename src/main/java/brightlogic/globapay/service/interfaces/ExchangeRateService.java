package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.dto.request.ExchangeRateRequest;
import brightlogic.globapay.dto.response.ExchangeRateResponse;

import java.util.List;
import java.util.UUID;

public interface ExchangeRateService {

    ExchangeRateResponse createRate(ExchangeRateRequest request);

    ExchangeRateResponse getLatestRate(CurrencyType sourceCurrency, CurrencyType targetCurrency);

    List<ExchangeRateResponse> getAllRates();

    void deactivateRate(UUID rateId);

    void validateCurrency(CurrencyType currency);

    double getExchangeRate(CurrencyType currency, CurrencyType currencyType);
}
