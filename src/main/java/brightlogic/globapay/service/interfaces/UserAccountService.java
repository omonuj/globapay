package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;

import java.util.List;
import java.util.UUID;

public interface UserAccountService {

    UserAccountResponse createUser(UserAccountRequest request);

    UserAccountResponse getUserById(UUID userId);

    UserAccountResponse getUserByEmail(String email);

    UserAccountResponse updateUser(UUID userId, UserAccountRequest request);

    void deactivateUser(UUID userId);

    List<UserAccountResponse> getAllUsers();

    void deleteUser(UUID userId);
}
