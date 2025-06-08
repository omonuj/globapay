package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.model.PaymentTransaction;
import brightlogic.globapay.dto.request.CreatePaymentRequest;
import brightlogic.globapay.dto.response.PaymentResponse;
import brightlogic.globapay.domain.enums.PaymentStatus;
import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", expression = "java(PaymentStatus.PENDING)"),
            @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())"),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "paymentMethod", source = "paymentMethod")
    })
    PaymentTransaction toEntity(CreatePaymentRequest dto);

    @Mappings({
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "paymentMethod", source = "paymentMethod"),
            @Mapping(target = "createdAt", source = "createdAt")
    })
    PaymentResponse toResponse(PaymentTransaction entity);

    // Custom mappings

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(ZoneId.systemDefault()).toInstant() : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? instant.atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    // These must be implemented if PaymentMethodType and PaymentMethod aren't directly mappable
    PaymentMethod map(PaymentMethodType type);
    PaymentMethodType map(PaymentMethod method);
}
