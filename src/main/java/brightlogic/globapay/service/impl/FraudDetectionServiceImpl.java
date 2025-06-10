package brightlogic.globapay.service.impl;

import brightlogic.globapay.domain.model.FraudDetectionRecord;
import brightlogic.globapay.dto.request.FraudDetectionRequest;
import brightlogic.globapay.dto.response.FraudDetectionResponse;
import brightlogic.globapay.event.FraudDetectionEvent;
import brightlogic.globapay.mapper.FraudDetectionMapper;
import brightlogic.globapay.repository.FraudDetectionRecordRepository;
import brightlogic.globapay.service.interfaces.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final FraudDetectionRecordRepository repository;
    private final FraudDetectionMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public FraudDetectionResponse createDetection(FraudDetectionRequest request) {
        FraudDetectionRecord record = mapper.toEntity(request);
        FraudDetectionRecord saved = repository.save(record);

        FraudDetectionEvent event = new FraudDetectionEvent(
                saved.getFraudId(),
                saved.getUserId(),
                saved.getTransactionId(),
                saved.getReason(),
                saved.isFlagged()
        );
        eventPublisher.publishEvent(event);

        return mapper.toResponse(saved);
    }

    @Override
    public List<FraudDetectionResponse> getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FraudDetectionResponse> getByTransactionId(UUID transactionId) {
        return repository.findByTransactionId(transactionId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
