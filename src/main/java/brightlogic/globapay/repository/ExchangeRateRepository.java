package brightlogic.globapay.repository;

import brightlogic.globapay.domain.enums.CurrencyType;
import brightlogic.globapay.domain.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    Optional<ExchangeRate> findTopBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(
            CurrencyType sourceCurrency,
            CurrencyType targetCurrency
    );
}
