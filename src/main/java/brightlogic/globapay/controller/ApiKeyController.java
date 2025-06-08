package brightlogic.globapay.controller;

import brightlogic.globapay.service.interfaces.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/apikey")
@RequiredArgsConstructor
public class ApiKeyController {


    private final ApiKeyService apiKeyService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateKey(@RequestParam String key) {
        boolean valid = apiKeyService.isValid(key);
        return ResponseEntity.ok(valid);
    }
}
