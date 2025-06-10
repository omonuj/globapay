package brightlogic.globapay.repository;

import brightlogic.globapay.domain.model.FraudDetectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FraudDetectionRecordRepository extends JpaRepository<FraudDetectionRecord, UUID> {

    List<FraudDetectionRecord> findByUserId(UUID userId);
    List<FraudDetectionRecord> findByTransactionId(UUID transactionId);
}
