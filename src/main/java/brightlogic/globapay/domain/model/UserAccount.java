package brightlogic.globapay.domain.model;

import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserAccount {

    @Id
    private String id = UUID.randomUUID().toString();

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private BigDecimal walletBalance;

    private String phoneNumber;

    private String Country;

    private boolean isActive = true;

}
