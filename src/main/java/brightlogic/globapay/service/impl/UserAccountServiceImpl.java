package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.UserAccount;
import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import brightlogic.globapay.repository.UserAccountRepository;
import brightlogic.globapay.service.interfaces.UserAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;

    @Override
    public UserAccountResponse createUser(UserAccountRequest request) {
        UserAccount user = new UserAccount();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setWalletBalance(request.getWalletBalance());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCountry(request.getCountry());
        user.setActive(request.isActive());

        UserAccount saved = repository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserAccountResponse getUserById(UUID userId) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Override
    public UserAccountResponse getUserByEmail(String email) {
        UserAccount user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Override
    public UserAccountResponse updateUser(UUID userId, UserAccountRequest request) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setWalletBalance(request.getWalletBalance());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCountry(request.getCountry());
        user.setActive(request.isActive());

        UserAccount updated = repository.save(user);
        return toResponse(updated);
    }

    @Override
    public void deactivateUser(UUID userId) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        repository.save(user);
    }

    @Override
    public List<UserAccountResponse> getAllUsers() {
        List<UserAccount> users = repository.findAll();
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(UUID userId) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        repository.delete(user);
    }

    private UserAccountResponse toResponse(UserAccount user) {
        UserAccountResponse res = new UserAccountResponse();
        res.setUserId(user.getUserId());
        res.setFullName(user.getFullName());
        res.setEmail(user.getEmail());
        res.setWalletBalance(user.getWalletBalance());
        res.setPhoneNumber(user.getPhoneNumber());
        res.setCountry(user.getCountry());
        res.setActive(user.isActive());
        return res;
    }
}
