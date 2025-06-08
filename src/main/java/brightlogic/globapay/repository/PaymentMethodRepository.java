package brightlogic.globapay.repository;

import brightlogic.globapay.domain.enums.PaymentMethodType;
import brightlogic.globapay.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
    List<PaymentMethod> findByUserId(UUID userId);
    List<PaymentMethod> findByUserIdAndType(UUID userId, PaymentMethodType type);
}
