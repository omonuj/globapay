package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import brightlogic.globapay.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {


    private final UserAccountService service;

    @PostMapping
    public ResponseEntity<UserAccountResponse> createUser(@RequestBody UserAccountRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserAccountResponse> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserAccountResponse>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
