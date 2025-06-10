package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.ApiKey;
import brightlogic.globapay.repository.ApiKeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiKeyServiceImplTest {

    private ApiKeyRepository apiKeyRepository;
    private ApiKeyServiceImpl apiKeyService;

    @BeforeEach
    void setUp() {
        apiKeyRepository = mock(ApiKeyRepository.class);
        apiKeyService = new ApiKeyServiceImpl(apiKeyRepository);
    }

    @Test
    void findByKey_shouldReturnApiKey_whenKeyExists() {
        String key = "someKey";
        ApiKey mockApiKey = new ApiKey();
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.of(mockApiKey));

        Optional<ApiKey> result = apiKeyService.findByKey(key);

        assertTrue(result.isPresent());
        assertEquals(mockApiKey, result.get());
    }

    @Test
    void findByKey_shouldReturnEmpty_whenKeyDoesNotExist() {
        String key = "missingKey";
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.empty());

        Optional<ApiKey> result = apiKeyService.findByKey(key);

        assertFalse(result.isPresent());
    }

    @Test
    void isValid_shouldReturnTrue_whenApiKeyIsActiveAndNotExpired() {
        String key = "validKey";
        ApiKey mockApiKey = new ApiKey();
        mockApiKey.setActive(true);
        mockApiKey.setExpiresAt(LocalDateTime.now().plusDays(1));
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.of(mockApiKey));

        boolean isValid = apiKeyService.isValid(key);

        assertTrue(isValid);
    }

    @Test
    void isValid_shouldReturnFalse_whenApiKeyIsInactive() {
        String key = "inactiveKey";
        ApiKey mockApiKey = new ApiKey();
        mockApiKey.setActive(false);
        mockApiKey.setExpiresAt(LocalDateTime.now().plusDays(1));
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.of(mockApiKey));

        boolean isValid = apiKeyService.isValid(key);

        assertFalse(isValid);
    }

    @Test
    void isValid_shouldReturnFalse_whenApiKeyIsExpired() {
        String key = "expiredKey";
        ApiKey mockApiKey = new ApiKey();
        mockApiKey.setActive(true);
        mockApiKey.setExpiresAt(LocalDateTime.now().minusDays(1));
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.of(mockApiKey));

        boolean isValid = apiKeyService.isValid(key);

        assertFalse(isValid);
    }

    @Test
    void isValid_shouldReturnFalse_whenApiKeyNotFound() {
        String key = "notFound";
        when(apiKeyRepository.findByKey(key)).thenReturn(Optional.empty());

        boolean isValid = apiKeyService.isValid(key);

        assertFalse(isValid);
    }

    @Test
    void save_shouldGenerateApiKeyAndSetDefaults() {
        ApiKey input = new ApiKey();
        input.setClientName("Test Client");
        input.setActive(true);
        input.setExpiresAt(LocalDateTime.now().plusDays(30));

        when(apiKeyRepository.save(any(ApiKey.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApiKey saved = apiKeyService.save(input);

        assertNotNull(saved.getApiKeyId());
        assertNotNull(saved.getKey());
        assertNotNull(saved.getCreatedAt());
        assertEquals("Test Client", saved.getClientName());
    }

    @Test
    void save_shouldCallRepositorySaveWithModifiedEntity() {
        ApiKey input = new ApiKey();
        input.setClientName("Client A");
        input.setActive(true);
        input.setExpiresAt(LocalDateTime.now().plusDays(10));

        when(apiKeyRepository.save(any(ApiKey.class))).thenAnswer(invocation -> invocation.getArgument(0));

        apiKeyService.save(input);

        ArgumentCaptor<ApiKey> captor = ArgumentCaptor.forClass(ApiKey.class);
        verify(apiKeyRepository, times(1)).save(captor.capture());

        ApiKey captured = captor.getValue();
        assertNotNull(captured.getApiKeyId());
        assertNotNull(captured.getKey());
        assertNotNull(captured.getCreatedAt());
        assertEquals("Client A", captured.getClientName());
        assertTrue(captured.isActive());
    }
}