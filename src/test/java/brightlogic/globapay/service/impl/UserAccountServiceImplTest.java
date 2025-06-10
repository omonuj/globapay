package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.UserAccount;
import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import brightlogic.globapay.mapper.UserAccountMapper;
import brightlogic.globapay.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAccountServiceImplTest {

    private UserAccountRepository repository;
    private UserAccountMapper mapper;
    private UserAccountServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(UserAccountRepository.class);
        mapper = mock(UserAccountMapper.class);
        service = new UserAccountServiceImpl(repository, mapper);
    }

    @Test
    void createUser_shouldReturnSavedResponse() {
        UserAccountRequest request = new UserAccountRequest();
        request.setFullName("Jane Doe");
        request.setEmail("jane@example.com");
        request.setWalletBalance(BigDecimal.valueOf(500));
        request.setPhoneNumber("1234567890");
        request.setCountry("NG");
        request.setActive(true);

        UserAccount userEntity = new UserAccount();
        UserAccount savedUser = new UserAccount();
        UserAccountResponse response = new UserAccountResponse();

        when(mapper.toEntity(request)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(savedUser);
        when(mapper.toResponse(savedUser)).thenReturn(response);

        UserAccountResponse result = service.createUser(request);

        assertEquals(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(userEntity);
        verify(mapper).toResponse(savedUser);
    }

    @Test
    void getUserById_shouldReturnResponseIfFound() {
        UUID userId = UUID.randomUUID();
        UserAccount user = new UserAccount();
        UserAccountResponse response = new UserAccountResponse();

        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);

        UserAccountResponse result = service.getUserById(userId);

        assertEquals(response, result);
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        UUID userId = UUID.randomUUID();
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getUserById(userId));
    }

    @Test
    void getUserByEmail_shouldReturnResponseIfFound() {
        String email = "john@example.com";
        UserAccount user = new UserAccount();
        UserAccountResponse response = new UserAccountResponse();

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);

        UserAccountResponse result = service.getUserByEmail(email);

        assertEquals(response, result);
    }

    @Test
    void getUserByEmail_shouldThrowIfNotFound() {
        when(repository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getUserByEmail("notfound@example.com"));
    }

    @Test
    void updateUser_shouldUpdateAndReturnResponse() {
        UUID userId = UUID.randomUUID();
        UserAccountRequest request = new UserAccountRequest();
        UserAccount existingUser = new UserAccount();
        UserAccount updatedUser = new UserAccount();
        UserAccountResponse response = new UserAccountResponse();

        when(repository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(mapper).updateEntityFromRequest(request, existingUser);
        when(repository.save(existingUser)).thenReturn(updatedUser);
        when(mapper.toResponse(updatedUser)).thenReturn(response);

        UserAccountResponse result = service.updateUser(userId, request);

        assertEquals(response, result);
    }

    @Test
    void deactivateUser_shouldSetActiveFalseAndSave() {
        UUID userId = UUID.randomUUID();
        UserAccount user = new UserAccount();
        user.setActive(true);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        service.deactivateUser(userId);

        assertFalse(user.isActive());
        verify(repository).save(user);
    }

    @Test
    void getAllUsers_shouldReturnMappedList() {
        List<UserAccount> users = Arrays.asList(new UserAccount(), new UserAccount());
        List<UserAccountResponse> responses = Arrays.asList(new UserAccountResponse(), new UserAccountResponse());

        when(repository.findAll()).thenReturn(users);
        when(mapper.toResponse(users.get(0))).thenReturn(responses.get(0));
        when(mapper.toResponse(users.get(1))).thenReturn(responses.get(1));

        List<UserAccountResponse> result = service.getAllUsers();

        assertEquals(responses, result);
    }

    @Test
    void deleteUser_shouldRemoveIfFound() {
        UUID userId = UUID.randomUUID();
        UserAccount user = new UserAccount();

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        service.deleteUser(userId);

        verify(repository).delete(user);
    }

    @Test
    void deleteUser_shouldThrowIfNotFound() {
        UUID userId = UUID.randomUUID();
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deleteUser(userId));
    }
}
