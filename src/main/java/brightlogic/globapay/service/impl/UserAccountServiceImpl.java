package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.UserAccount;
import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import brightlogic.globapay.mapper.UserAccountMapper;
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
    private final UserAccountMapper mapper;

    @Override
    public UserAccountResponse createUser(UserAccountRequest request) {
        UserAccount user = mapper.toEntity(request);
        UserAccount saved = repository.save(user);
        return mapper.toResponse(saved);
    }

    @Override
    public UserAccountResponse getUserById(UUID userId) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toResponse(user);
    }

    @Override
    public UserAccountResponse getUserByEmail(String email) {
        UserAccount user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toResponse(user);
    }

    @Override
    public UserAccountResponse updateUser(UUID userId, UserAccountRequest request) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        mapper.updateEntityFromRequest(request, user);
        UserAccount updated = repository.save(user);
        return mapper.toResponse(updated);
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
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(UUID userId) {
        UserAccount user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        repository.delete(user);
    }
}
