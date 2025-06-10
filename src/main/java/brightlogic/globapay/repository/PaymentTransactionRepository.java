package brightlogic.globapay.repository;

import brightlogic.globapay.domain.enums.PaymentStatus;
import brightlogic.globapay.domain.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
        Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);

        List<PaymentTransaction> findByUserId(UUID userId);

        List<PaymentTransaction> findByUserIdAndStatus(UUID userId, PaymentStatus status);

        Optional<PaymentTransaction> findByTransactionId(UUID transactionId);
}
