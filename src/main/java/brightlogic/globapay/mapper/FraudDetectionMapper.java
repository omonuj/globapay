package brightlogic.globapay.mapper;

import brightlogic.globapay.domain.model.FraudDetectionRecord;
import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FraudDetectionMapper {

    public FraudDetectionRecord toEntity(FraudDetectionRequest request) {
        FraudDetectionRecord record = new FraudDetectionRecord();
        record.setUserId(request.getUserId());
        record.setTransactionId(request.getTransactionId());
        record.setReason(request.getReason());
        record.setFlagged(request.isFlagged());
        record.setDetectedAt(LocalDateTime.now());
        return record;
    }

    public FraudDetectionResponse toResponse(FraudDetectionRecord record) {
        FraudDetectionResponse response = new FraudDetectionResponse();
        response.setId(record.getFraudId());
        response.setUserId(record.getUserId());
        response.setTransactionId(record.getTransactionId());
        response.setReason(record.getReason());
        response.setFlagged(record.isFlagged());
        response.setDetectedAt(record.getDetectedAt());
        return response;
    }
}
