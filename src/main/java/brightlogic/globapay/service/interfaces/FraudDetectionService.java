package brightlogic.globapay.service.interfaces;

import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;

import java.util.List;
import java.util.UUID;

public interface FraudDetectionService {

    FraudDetectionResponse createDetection(FraudDetectionRequest request);
    List<FraudDetectionResponse> getByUserId(UUID userId);
    List<FraudDetectionResponse> getByTransactionId(UUID transactionId);
}
