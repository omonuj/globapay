package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.model.PaymentMethod;
import brightlogic.globapay.dto.request.PaymentMethodRequest;
import brightlogic.globapay.dto.response.PaymentMethodResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    public PaymentMethod toModel(PaymentMethodRequest request) {
        return new PaymentMethod(
                request.getType(),
                request.getProvider(),
                request.getMaskedDetails(),
                request.getReferenceId()
        );
    }

    public PaymentMethodResponse toResponse(PaymentMethod model) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setType(model.getType());
        response.setProvider(model.getProvider());
        response.setMaskedDetails(model.getMaskedDetails());
        response.setReferenceId(model.getReferenceId());
        return response;
    }
}
