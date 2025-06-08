package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.FraudDetectionRecord;
import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import brightlogic.globapay.repository.FraudDetectionRecordRepository;
import brightlogic.globapay.service.interfaces.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final FraudDetectionRecordRepository repository;

    @Override
    public FraudDetectionResponse createDetection(FraudDetectionRequest request) {
        FraudDetectionRecord record = new FraudDetectionRecord();
        record.setUserId(request.getUserId());
        record.setTransactionId(request.getTransactionId());
        record.setReason(request.getReason());
        record.setFlagged(request.isFlagged());
        record.setDetectedAt(LocalDateTime.now());

        FraudDetectionRecord saved = repository.save(record);

        return toResponse(saved);
    }

    @Override
    public List<FraudDetectionResponse> getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FraudDetectionResponse> getByTransactionId(UUID transactionId) {
        return repository.findByTransactionId(transactionId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private FraudDetectionResponse toResponse(FraudDetectionRecord record) {

        FraudDetectionResponse res = new FraudDetectionResponse();
        res.setId(record.getFraudId());
        res.setUserId(record.getUserId());
        res.setTransactionId(record.getTransactionId());
        res.setReason(record.getReason());
        res.setFlagged(record.isFlagged());
        res.setDetectedAt(record.getDetectedAt());

        return res;
    }
}
