package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.dto.request.PaymentTransactionRequest;
import brightlogic.globapay.dto.response.PaymentTransactionResponse;
import brightlogic.globapay.exception.UnsupportedPaymentMethodException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exchangeRate", ignore = true)
    @Mapping(target = "amountInBaseCurrency", ignore = true)
    PaymentTransaction toEntity(PaymentTransactionRequest dto);


    PaymentTransactionResponse toResponse(PaymentTransaction entity);

    default PaymentMethodType map(String method) {
        try {
            return PaymentMethodType.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedPaymentMethodException("Unsupported method: " + method);
        }
    }


    default Instant map(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    // Optionally add reverse mapping if needed
    default LocalDateTime map(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
