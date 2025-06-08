package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.model.ExchangeRate;
import brightlogic.globapay.dto.request.ExchangeRateRequest;
import brightlogic.globapay.dto.response.ExchangeRateResponse;
import brightlogic.globapay.repository.ExchangeRateRepository;
import brightlogic.globapay.service.interfaces.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository repository;

    @Override
    public ExchangeRateResponse createRate(ExchangeRateRequest request) {
        ExchangeRate rate = new ExchangeRate();
        rate.setSourceCurrency(request.getSourceCurrency());
        rate.setTargetCurrency(request.getTargetCurrency());
        rate.setRate(request.getRate());
        rate.setActive(request.isActive());

        ExchangeRate saved = repository.save(rate);
        return toResponse(saved);
    }

    @Override
    public ExchangeRateResponse getLatestRate(CurrencyType sourceCurrency, CurrencyType targetCurrency) {
        ExchangeRate rate = repository
                .findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(sourceCurrency, targetCurrency)
                .orElseThrow(() -> new RuntimeException("No exchange rate found for specified currencies."));
        return toResponse(rate);
    }

    @Override
    public List<ExchangeRateResponse> getAllRates() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateRate(UUID rateId) {
        ExchangeRate rate = repository.findById(rateId)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found."));
        rate.setActive(false);
        repository.save(rate);
    }


    private ExchangeRateResponse toResponse(ExchangeRate rate) {
        ExchangeRateResponse res = new ExchangeRateResponse();
        res.setExchangeRateId(rate.getExchangeRateId());
        res.setSourceCurrency(rate.getSourceCurrency());
        res.setTargetCurrency(rate.getTargetCurrency());
        res.setRate(rate.getRate());
        res.setIsActive(rate.isActive());
        return res;
    }
}
