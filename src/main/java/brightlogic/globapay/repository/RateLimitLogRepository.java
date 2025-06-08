package brightlogic.globapay.repository;

import brightlogic.globapay.domain.model.RateLimitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RateLimitLogRepository extends JpaRepository<RateLimitLog, UUID> {

    List<RateLimitLog> findByUserId(UUID userId);
    List<RateLimitLog> findByIpAddress(String ipAddress);
}
