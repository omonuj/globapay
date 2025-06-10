package brightlogic.globapay.controller;


import brightlogic.globapay.dto.request.PaymentMethodRequest;
import brightlogic.globapay.dto.response.PaymentMethodResponse;
import brightlogic.globapay.mapper.PaymentMethodMapper;
import brightlogic.globapay.service.interfaces.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;
    private final PaymentMethodMapper paymentMethodMapper;

    @PostMapping
    public PaymentMethodResponse handlePaymentMethod(@RequestBody PaymentMethodRequest request) {
        var model = paymentMethodMapper.toModel(request);
        var result = paymentMethodService.process(model);
        return paymentMethodMapper.toResponse(result);
    }
}
