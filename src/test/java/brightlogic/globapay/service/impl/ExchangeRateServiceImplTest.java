package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.model.ExchangeRate;
import brightlogic.globapay.dto.request.ExchangeRateRequest;
import brightlogic.globapay.dto.response.ExchangeRateResponse;
import brightlogic.globapay.exception.exchangerateexception.ExchangeRateNotFoundException;
import brightlogic.globapay.exception.exchangerateexception.InvalidCurrencyException;
import brightlogic.globapay.mapper.ExchangeRateMapper;
import brightlogic.globapay.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceImplTest {

    private ExchangeRateRepository repository;
    private ExchangeRateMapper mapper;
    private ExchangeRateServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ExchangeRateRepository.class);
        mapper = mock(ExchangeRateMapper.class);
        service = new ExchangeRateServiceImpl(repository, mapper);
    }

    @Test
    void createRate_shouldSaveAndReturnResponse() {
        ExchangeRateRequest request = new ExchangeRateRequest();
        request.setSourceCurrency("USD");
        request.setTargetCurrency("EUR");
        request.setRate(BigDecimal.valueOf(1.1));
        request.setActive(true);

        ExchangeRate entity = new ExchangeRate();
        ExchangeRate savedEntity = new ExchangeRate();
        ExchangeRateResponse expectedResponse = new ExchangeRateResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(expectedResponse);

        ExchangeRateResponse response = service.createRate(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getLatestRate_shouldReturnExchangeRate() {
        CurrencyType from = CurrencyType.USD;
        CurrencyType to = CurrencyType.EUR;
        ExchangeRate rate = new ExchangeRate();
        ExchangeRateResponse response = new ExchangeRateResponse();

        when(repository.findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(from, to))
                .thenReturn(Optional.of(rate));
        when(mapper.toResponse(rate)).thenReturn(response);

        ExchangeRateResponse result = service.getLatestRate(from, to);
        assertEquals(response, result);
    }

    @Test
    void getLatestRate_shouldThrowExceptionIfNotFound() {
        CurrencyType from = CurrencyType.USD;
        CurrencyType to = CurrencyType.EUR;

        when(repository.findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(from, to))
                .thenReturn(Optional.empty());

        assertThrows(ExchangeRateNotFoundException.class, () -> service.getLatestRate(from, to));
    }

    @Test
    void getExchangeRate_shouldReturnDoubleRate() {
        CurrencyType from = CurrencyType.USD;
        CurrencyType to = CurrencyType.EUR;
        ExchangeRate rate = new ExchangeRate();
        rate.setRate(BigDecimal.valueOf(1.2));

        when(repository.findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(from, to))
                .thenReturn(Optional.of(rate));

        double result = service.getExchangeRate(from, to);
        assertEquals(1.2, result);
    }

    @Test
    void getExchangeRate_shouldThrowIfRateNotFound() {
        when(repository.findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(ExchangeRateNotFoundException.class,
                () -> service.getExchangeRate(CurrencyType.USD, CurrencyType.EUR));
    }

    @Test
    void validateCurrency_shouldPassForValidCurrency() {
        assertDoesNotThrow(() -> service.validateCurrency(CurrencyType.USD));
    }

    @Test
    void validateCurrency_shouldThrowForNullCurrency() {
        assertThrows(InvalidCurrencyException.class, () -> service.validateCurrency(null));
    }

    @Test
    void deactivateRate_shouldSetInactive() {
        UUID rateId = UUID.randomUUID();
        ExchangeRate rate = new ExchangeRate();
        rate.setActive(true);

        when(repository.findById(rateId)).thenReturn(Optional.of(rate));

        service.deactivateRate(rateId);
        assertFalse(rate.isActive());
        verify(repository).save(rate);
    }

    @Test
    void deactivateRate_shouldThrowIfRateNotFound() {
        UUID rateId = UUID.randomUUID();
        when(repository.findById(rateId)).thenReturn(Optional.empty());

        assertThrows(ExchangeRateNotFoundException.class, () -> service.deactivateRate(rateId));
    }

    @Test
    void getAllRates_shouldReturnMappedList() {
        ExchangeRate rate = new ExchangeRate();
        ExchangeRateResponse response = new ExchangeRateResponse();

        when(repository.findAll()).thenReturn(List.of(rate));
        when(mapper.toResponse(rate)).thenReturn(response);

        List<ExchangeRateResponse> result = service.getAllRates();
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }
}
