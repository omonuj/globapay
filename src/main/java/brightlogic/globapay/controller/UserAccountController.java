package brightlogic.globapay.controller;

import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import brightlogic.globapay.service.interfaces.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Account Controller", description = "Handles user account operations")
public class UserAccountController {

    private final UserAccountService service;

    @PostMapping("/create")
    @Operation(summary = "Create a new user", description = "Registers a new user account")
    public ResponseEntity<UserAccountResponse> createUser(@RequestBody UserAccountRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieves user account details using their ID")
    public ResponseEntity<UserAccountResponse> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping("/users")
    @Operation(summary = "Retrieve all users", description = "Fetches all registered user accounts")
    public ResponseEntity<List<UserAccountResponse>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID", description = "Removes a user account from the system")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}