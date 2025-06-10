package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.model.ExchangeRate;
import brightlogic.globapay.dto.request.ExchangeRateRequest;
import brightlogic.globapay.dto.response.ExchangeRateResponse;
import brightlogic.globapay.exception.exchangerateexception.ExchangeRateNotFoundException;
import brightlogic.globapay.exception.exchangerateexception.InvalidCurrencyException;
import brightlogic.globapay.mapper.ExchangeRateMapper;
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
    private final ExchangeRateMapper mapper;

    @Override
    public ExchangeRateResponse createRate(ExchangeRateRequest request) {
        ExchangeRate rate = mapper.toEntity(request);
        ExchangeRate saved = repository.save(rate);
        return mapper.toResponse(saved);
    }

    @Override
    public ExchangeRateResponse getLatestRate(CurrencyType source, CurrencyType target) {
        ExchangeRate rate = repository.findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(source, target)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found."));
        return mapper.toResponse(rate);
    }

    @Override
    public List<ExchangeRateResponse> getAllRates() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateRate(UUID rateId) {
        ExchangeRate rate = repository.findById(rateId)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found."));
        rate.setActive(false);
        repository.save(rate);
    }
    @Override
    public void validateCurrency(CurrencyType currency) {
        if (currency == null) {
            throw new InvalidCurrencyException("Currency cannot be null.");
        }

        boolean isValid = List.of(CurrencyType.values()).contains(currency);
        if (!isValid) {
            throw new InvalidCurrencyException("Unsupported currency: " + currency);
        }
    }

    @Override
    public double getExchangeRate(CurrencyType from, CurrencyType to) {
        if (from == null || to == null) {
            throw new InvalidCurrencyException("Source and target currencies must not be null.");
        }

        ExchangeRate latestRate = repository
                .findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(from, to)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found for " + from + " to " + to));

        return latestRate.getRate().doubleValue();
    }

}
