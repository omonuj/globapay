package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.RateLimitLogRequest;
import brightlogic.globapay.dto.response.RateLimitLogResponse;

import java.util.List;
import java.util.UUID;

public interface RateLimitLogService {


    RateLimitLogResponse logRequest(RateLimitLogRequest request);

    List<RateLimitLogResponse> getByUserId(UUID userId);

    List<RateLimitLogResponse> getByIpAddress(String ipAddress);

    List<RateLimitLogResponse> getAllLogs();
}
