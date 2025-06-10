package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.PaymentMethodRequest;
import brightlogic.globapay.dto.response.PaymentMethodResponse;
import brightlogic.globapay.mapper.PaymentMethodMapper;
import brightlogic.globapay.service.interfaces.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
@Tag(name = "Payment Method Controller", description = "Manages payment methods processing")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;
    private final PaymentMethodMapper paymentMethodMapper;

    @PostMapping("/payment")
    @Operation(summary = "Process a payment method", description = "Handles payment method operations based on request data")
    public PaymentMethodResponse handlePaymentMethod(@RequestBody PaymentMethodRequest request) {
        var model = paymentMethodMapper.toModel(request);
        var result = paymentMethodService.process(model);
        return paymentMethodMapper.toResponse(result);
    }
}